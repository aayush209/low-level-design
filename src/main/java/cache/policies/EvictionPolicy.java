package cache.policies;

import cache.exceptions.NoSuchElementException;

public interface EvictionPolicy<Key> {

    void keyAccessed(Key key) throws NoSuchElementException;

    /**
     * Evict key from cache as per eviction policy and return it*/
    Key evictKey() throws NoSuchElementException;
}
