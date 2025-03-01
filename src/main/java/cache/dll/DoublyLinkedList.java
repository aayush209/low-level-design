package cache.dll;

import cache.exceptions.NoSuchElementException;

public class DoublyLinkedList<Element> {

    DoublyLinkedListNode<Element> head; // dummy head
    DoublyLinkedListNode<Element> tail; // dummy tail

    public DoublyLinkedList(){
        head = new DoublyLinkedListNode<>(null);
        tail = new DoublyLinkedListNode<>(null);

        // as an initial step, join head and tail
        head.next = tail;
        tail.prev = head;
    }

    public boolean isItemPresent() {
        return head.next != tail;
    }

    public void addNodeAtLast(DoublyLinkedListNode<Element> node) {
        DoublyLinkedListNode<Element> tailPrev = tail.prev;
        tailPrev.next = node;
        node.next = tail;
        tail.prev = node;
        node.prev = tailPrev;
    }

    public void detachNode(DoublyLinkedListNode<Element> node) {
        // Just Simply modifying the pointers.
        if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public DoublyLinkedListNode<Element> addElementAtLast(Element element) throws NoSuchElementException {
        if (element == null) {
            throw new NoSuchElementException();
        }
        DoublyLinkedListNode<Element> newNode = new DoublyLinkedListNode<>(element);
        addNodeAtLast(newNode);
        return newNode;
    }

    public DoublyLinkedListNode<Element> getFirstNode() throws NoSuchElementException {
        DoublyLinkedListNode<Element> item = null;
        if (!isItemPresent()) {
            return null;
        }
        return head.next;
    }

    public DoublyLinkedListNode<Element> getLastNode() throws NoSuchElementException {
        DoublyLinkedListNode<Element> item = null;
        if (!isItemPresent()) {
            return null;
        }
        return tail.prev;
    }
}
