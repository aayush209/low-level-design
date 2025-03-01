package logging.mediumarticle;

// Enum to define logger levels
public enum LoggerLevel {
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5);

    private final int level;

    LoggerLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
