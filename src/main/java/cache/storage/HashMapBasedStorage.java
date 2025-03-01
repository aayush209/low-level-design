package cache.storage;

import cache.exceptions.NotFoundException;
import cache.exceptions.StorageFullException;
import java.util.HashMap;
import java.util.Map;

public class HashMapBasedStorage<Key, Value> implements Storage<Key, Value> {

    Map<Key, Value> map = new HashMap<>();

    @Override
    public void add(Key key, Value value) throws StorageFullException {
        map.put(key, value);
    }

    @Override
    public void remove(Key key) throws NotFoundException {
        map.remove(key);
    }

    @Override
    public Value get(Key key) throws NotFoundException {
        return map.get(key);
    }
}
