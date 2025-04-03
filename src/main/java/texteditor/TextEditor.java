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
    
    private final StringBuilder leftText;
    private final StringBuilder rightText;
    private int cursorPosition;
    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;
    private final ReadWriteLock lock;
    private final List<TextChangeListener> listeners;
    private String lastTenCharsCache;
    private boolean cacheInvalid;

    /**
     * Interface for text change listeners
     */
    public interface TextChangeListener {
        void onTextChanged(String newText, int cursorPosition);
    }

    /**
     * Abstract command class for implementing command pattern
     */
    private abstract class Command {
        protected final String text;
        protected final int position;
        
        protected Command(String text, int position) {
            this.text = text;
            this.position = position;
        }
        
        abstract void execute();
        abstract void undo();
    }

    /**
     * Command for adding text
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
     * Command for deleting text
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
     * Adds a listener for text change events
     */
    public void addTextChangeListener(TextChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a text change listener
     */
    public void removeTextChangeListener(TextChangeListener listener) {
        listeners.remove(listener);
    }

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
     * Undo the last operation
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
     * Redo the last undone operation
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

    public int getCursorPosition() {
        lock.readLock().lock();
        try {
            return cursorPosition;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getTextLength() {
        lock.readLock().lock();
        try {
            return leftText.length() + rightText.length();
        } finally {
            lock.readLock().unlock();
        }
    }

    private void invalidateCache() {
        cacheInvalid = true;
    }

    String getLastTenCharacters() {
        if (!cacheInvalid) {
            return lastTenCharsCache;
        }
        
        lastTenCharsCache = leftText.substring(Math.max(leftText.length() - 10, 0));
        cacheInvalid = false;
        return lastTenCharsCache;
    }

    private void notifyListeners() {
        String currentText = leftText.toString() + rightText.toString();
        for (TextChangeListener listener : listeners) {
            listener.onTextChanged(currentText, cursorPosition);
        }
    }
}