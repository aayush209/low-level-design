package texteditor;

/**
 * Demo class to showcase the functionality of TextEditor.
 * This class demonstrates advanced features and design patterns used in the implementation.
 */
public class TextEditorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced TextEditor Demo ===\n");
        
        // Create editor with text change listener
        TextEditor editor = new TextEditor();
        editor.addTextChangeListener((newText, cursorPos) -> 
            System.out.println("Text changed: " + newText + " (cursor at: " + cursorPos + ")"));
        
        // Test Case 1: Basic Operations with Undo/Redo
        System.out.println("Test Case 1: Basic Operations with Undo/Redo");
        editor.addText("Hello");
        editor.addText(" World");
        System.out.println("Initial text added");
        editor.undo();
        System.out.println("After undo: " + editor.getTextLength() + " characters");
        editor.redo();
        System.out.println("After redo: " + editor.getTextLength() + " characters");
        System.out.println();
        
        // Test Case 2: Thread Safety Demonstration
        System.out.println("Test Case 2: Thread Safety");
        TextEditor threadSafeEditor = new TextEditor();
        Runnable writer = () -> {
            for (int i = 0; i < 100; i++) {
                threadSafeEditor.addText("a");
            }
        };
        Runnable reader = () -> {
            for (int i = 0; i < 100; i++) {
                threadSafeEditor.getTextLength();
            }
        };
        
        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(reader);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread-safe operations completed successfully");
        System.out.println();
        
        // Test Case 3: Complex Operations
        System.out.println("Test Case 3: Complex Operations");
        TextEditor complexEditor = new TextEditor();
        complexEditor.addText("Hello World");
        System.out.println("Initial text: Hello World");
        complexEditor.cursorLeft(5);
        System.out.println("After moving cursor left: " + complexEditor.getLastTenCharacters());
        complexEditor.deleteText(2);
        System.out.println("After deleting 2 chars: " + complexEditor.getLastTenCharacters());
        complexEditor.undo();
        System.out.println("After undo: " + complexEditor.getLastTenCharacters());
        System.out.println();
        
        // Test Case 4: Edge Cases and Error Handling
        System.out.println("Test Case 4: Edge Cases and Error Handling");
        TextEditor edgeEditor = new TextEditor();
        
        System.out.println("Testing invalid inputs:");
        try {
            edgeEditor.addText(null);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Null text rejected: " + e.getMessage());
        }
        
        try {
            edgeEditor.addText("");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Empty text rejected: " + e.getMessage());
        }
        
        try {
            edgeEditor.deleteText(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Negative delete count rejected: " + e.getMessage());
        }
        
        try {
            edgeEditor.cursorLeft(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Negative cursor movement rejected: " + e.getMessage());
        }
        System.out.println();
        
        // Test Case 5: Performance Optimization
        System.out.println("Test Case 5: Performance Optimization");
        TextEditor perfEditor = new TextEditor();
        StringBuilder largeText = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeText.append("a");
        }
        long startTime = System.currentTimeMillis();
        perfEditor.addText(largeText.toString());
        System.out.println("Added 1000 characters");
        for (int i = 0; i < 100; i++) {
            perfEditor.getLastTenCharacters(); // Should use cache
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken for 100 getLastTenCharacters calls: " + (endTime - startTime) + "ms");
        System.out.println();
        
        // Test Case 6: Observer Pattern
        System.out.println("Test Case 6: Observer Pattern");
        TextEditor observerEditor = new TextEditor();
        observerEditor.addTextChangeListener((text, pos) -> 
            System.out.println("Observer 1: Text changed to: " + text));
        observerEditor.addTextChangeListener((text, pos) -> 
            System.out.println("Observer 2: Cursor position: " + pos));
        observerEditor.addText("Testing observers");
        System.out.println();
    }
} 