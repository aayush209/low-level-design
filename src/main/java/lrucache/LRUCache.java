package lrucache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public synchronized V get(K key) {
        if(cache.containsKey(key)){
            Node<K, V> node = cache.get(key);
            removeFromDLL(node);
            insertAfterHeadInDLL(node);
            return node.value; // if found in cache i.e. cache hit
        }else{
            return null; // if not found in cache i.e. cache miss
        }
    }

    public synchronized void put(K key, V value) {
        if(cache.containsKey(key)){ // if the ele is present in cache
            Node<K, V> node = cache.get(key);
            removeFromDLL(node);
        }
        if(cache.size() == capacity){ // if the cache is at capacity
            removeFromDLL(tail.prev); // remove the LRU element i.e. nearest to the tail
        }

        //irrespective of the above 2 cases, insert the node at the head
        insertAfterHeadInDLL(new Node<K, V>(key, value));
    }

    private void removeFromDLL(Node<K, V> node) {
        //remove key from cache map
        cache.remove(node.key);

        //remove node from the DLL
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertAfterHeadInDLL(Node<K, V> node) {
        //add key to the cache map
        cache.put(node.key, node);

        // add the node just after the head
        Node<K, V> headNext = head.next;
        head.next = node;
        node.prev = head;
        headNext.prev = node;
        node.next = headNext;
    }
}
