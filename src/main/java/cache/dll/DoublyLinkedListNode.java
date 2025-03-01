package cache.dll;

import lombok.Getter;

@Getter
public class DoublyLinkedListNode<Element> {

    DoublyLinkedListNode<Element> next;
    DoublyLinkedListNode<Element> prev;
    Element element;

    public DoublyLinkedListNode(Element element){
        this.element = element;
        this.next = null;
        this.prev = null;
    }
}
