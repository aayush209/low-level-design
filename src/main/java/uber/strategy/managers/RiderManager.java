package uber.strategy.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import uber.entities.Rider;

public class RiderManager {

    private static final Lock lock = new ReentrantLock();
    private static RiderManager instance;
    private final Map<String, Rider> riders = new HashMap<>();

    private RiderManager() {

    }

    public static RiderManager getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new RiderManager();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void addRider(String name, Rider rider) {
        riders.put(name, rider);
    }

    public Rider getRider(String name) {
        return riders.get(name);
    }
}
