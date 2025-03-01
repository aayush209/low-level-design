package cache.policies;

import cache.dll.DoublyLinkedList;
import cache.dll.DoublyLinkedListNode;
import cache.exceptions.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {

    private DoublyLinkedList<Key> doublyLinkedList;
    private Map<Key, DoublyLinkedListNode<Key>> mapper;

    public LRUEvictionPolicy() {
        this.doublyLinkedList = new DoublyLinkedList<>();
        this.mapper = new HashMap<>();
    }

    @Override
    public void keyAccessed(Key key) throws NoSuchElementException {
        if (mapper.containsKey(key)) {
            doublyLinkedList.detachNode(mapper.get(key));
            doublyLinkedList.addNodeAtLast(mapper.get(key));
        } else {
            DoublyLinkedListNode<Key> newNode = doublyLinkedList.addElementAtLast(key);
            mapper.put(key, newNode);
        }
    }

    @Override
    public Key evictKey() throws NoSuchElementException {
        DoublyLinkedListNode<Key> first = doublyLinkedList.getFirstNode();
        if(first == null) {
            return null;
        }
        doublyLinkedList.detachNode(first);
        return first.getElement();
    }
}
