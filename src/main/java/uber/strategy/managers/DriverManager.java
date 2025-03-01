package uber.strategy.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import uber.entities.Driver;

public class DriverManager {

    private static DriverManager instance;
    private static final Lock lock = new ReentrantLock();
    private final Map<String, Driver> drivers = new HashMap<>();

    private DriverManager() {

    }

    public static DriverManager getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new DriverManager();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void addDriver(String name, Driver driver) {
        drivers.put(name, driver);
    }

    public Driver getDriver(String name) {
        return drivers.get(name);
    }

    public Map<String, Driver> getDrivers() {
        return drivers;
    }
}
