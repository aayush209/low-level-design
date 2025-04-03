package texteditor;

import java.util.Stack;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.List;

/**
 * A sophisticated text editor implementation that supports advanced text manipulation operations.
 * This implementation uses multiple design patterns and provides thread-safe operations.
 */
public class TextEditor {
    private static final int MAX_CURSOR_MOVE = 10000;
    private static final int MAX_TEXT_LENGTH = 100000;
    private static final int CACHE_SIZE = 1000;
    
    // Builder Pattern: Immutable fields initialized in constructor
    private final StringBuilder leftText;
    private final StringBuilder rightText;
    private int cursorPosition;
    // Command Pattern: Stacks for undo/redo operations
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    // Strategy Pattern: Lock implementation for thread safety
    private final ReadWriteLock lock;
    // Observer Pattern: List of listeners for text change events
    private final List<TextChangeListener> listeners;
    // Template Method Pattern: Cache state management
    private String lastTenCharsCache;
    private boolean cacheInvalid;

    /**
     * Observer Pattern: Interface for text change listeners
     */
    public interface TextChangeListener {
        void onTextChanged(String newText, int cursorPosition);
    }

    /**
     * Command Pattern: Abstract command class defining the structure for all commands
     */
    private abstract class Command {
        protected final String text;
        protected final int position;
        
        protected Command(String text, int position) {
            this.text = text;
            this.position = position;
        }
        
        // Template Method Pattern: Abstract methods that must be implemented by concrete commands
        abstract void execute();
        abstract void undo();
    }

    /**
     * Command Pattern: Concrete command for adding text
     */
    private class AddTextCommand extends Command {
        public AddTextCommand(String text, int position) {
            super(text, position);
        }
        
        @Override
        void execute() {
            leftText.append(text);
            cursorPosition += text.length();
            invalidateCache();
            notifyListeners();
        }
        
        @Override
        void undo() {
            leftText.setLength(leftText.length() - text.length());
            cursorPosition -= text.length();
            invalidateCache();
            notifyListeners();
        }
    }

    /**
     * Command Pattern: Concrete command for deleting text
     */
    private class DeleteTextCommand extends Command {
        private final String deletedText;
        
        public DeleteTextCommand(int count, int position) {
            super(null, position);
            this.deletedText = leftText.substring(position - count, position);
        }
        
        @Override
        void execute() {
            leftText.setLength(position);
            cursorPosition = position;
            invalidateCache();
            notifyListeners();
        }
        
        @Override
        void undo() {
            leftText.append(deletedText);
            cursorPosition = position;
            invalidateCache();
            notifyListeners();
        }
    }

    // Builder Pattern: Constructor initializing all components
    public TextEditor() {
        this.leftText = new StringBuilder();
        this.rightText = new StringBuilder();
        this.cursorPosition = 0;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.lock = new ReentrantReadWriteLock();
        this.listeners = new ArrayList<>();
        this.cacheInvalid = true;
    }

    /**
     * Observer Pattern: Method to add new listeners for text change events
     */
    public void addTextChangeListener(TextChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Observer Pattern: Method to remove listeners
     */
    public void removeTextChangeListener(TextChangeListener listener) {
        listeners.remove(listener);
    }

    // Command Pattern: Operation wrapped in a command object
    public void addText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        lock.writeLock().lock();
        try {
            if (leftText.length() + rightText.length() + text.length() > MAX_TEXT_LENGTH) {
                throw new IllegalStateException("Maximum text length exceeded");
            }
            
            Command command = new AddTextCommand(text, cursorPosition);
            command.execute();
            undoStack.push(command);
            redoStack.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Command Pattern: Operation wrapped in a command object
    public int deleteText(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Number of characters to delete cannot be negative");
        }
        
        lock.writeLock().lock();
        try {
            int actualDeleteCount = Math.min(k, leftText.length());
            Command command = new DeleteTextCommand(actualDeleteCount, cursorPosition);
            command.execute();
            undoStack.push(command);
            redoStack.clear();
            return actualDeleteCount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Strategy Pattern: Thread-safe operation using ReadWriteLock
    public String cursorLeft(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Cursor movement cannot be negative");
        }
        if (k > MAX_CURSOR_MOVE) {
            throw new IllegalArgumentException("Cursor movement exceeds maximum allowed");
        }

        lock.writeLock().lock();
        try {
            int actualMoveCount = Math.min(k, leftText.length());
            for (int i = 0; i < actualMoveCount; ++i) {
                rightText.append(leftText.charAt(leftText.length() - 1));
                leftText.deleteCharAt(leftText.length() - 1);
                cursorPosition--;
            }
            invalidateCache();
            return getLastTenCharacters();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Strategy Pattern: Thread-safe operation using ReadWriteLock
    public String cursorRight(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("Cursor movement cannot be negative");
        }
        if (k > MAX_CURSOR_MOVE) {
            throw new IllegalArgumentException("Cursor movement exceeds maximum allowed");
        }

        lock.writeLock().lock();
        try {
            int actualMoveCount = Math.min(k, rightText.length());
            for (int i = 0; i < actualMoveCount; ++i) {
                char ch = rightText.charAt(rightText.length() - 1);
                leftText.append(ch);
                rightText.deleteCharAt(rightText.length() - 1);
                cursorPosition++;
            }
            invalidateCache();
            return getLastTenCharacters();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Command Pattern: Undo operation using command stack
     */
    public void undo() {
        lock.writeLock().lock();
        try {
            if (!undoStack.isEmpty()) {
                Command command = undoStack.pop();
                command.undo();
                redoStack.push(command);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Command Pattern: Redo operation using command stack
     */
    public void redo() {
        lock.writeLock().lock();
        try {
            if (!redoStack.isEmpty()) {
                Command command = redoStack.pop();
                command.execute();
                undoStack.push(command);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Strategy Pattern: Thread-safe read operation
    public int getCursorPosition() {
        lock.readLock().lock();
        try {
            return cursorPosition;
        } finally {
            lock.readLock().unlock();
        }
    }

    // Strategy Pattern: Thread-safe read operation
    public int getTextLength() {
        lock.readLock().lock();
        try {
            return leftText.length() + rightText.length();
        } finally {
            lock.readLock().unlock();
        }
    }

    // Template Method Pattern: Cache invalidation
    private void invalidateCache() {
        cacheInvalid = true;
    }

    // Template Method Pattern: Cache access
    String getLastTenCharacters() {
        if (!cacheInvalid) {
            return lastTenCharsCache;
        }
        
        lastTenCharsCache = leftText.substring(Math.max(leftText.length() - 10, 0));
        cacheInvalid = false;
        return lastTenCharsCache;
    }

    /**
     * Observer Pattern: Notifying all listeners of text changes
     */
    private void notifyListeners() {
        String currentText = leftText.toString() + rightText.toString();
        for (TextChangeListener listener : listeners) {
            listener.onTextChanged(currentText, cursorPosition);
        }
    }
}