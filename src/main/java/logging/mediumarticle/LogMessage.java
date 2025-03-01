package logging.mediumarticle;

// LogMessage public class for holding the log message data
public class LogMessage {

    private final LoggerLevel level;
    private final String message;
    private final long timestamp;

    public LogMessage(LoggerLevel level, String message) {
        this.level = level;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public LoggerLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + level + "] " + timestamp + " - " + message;
    }
}
