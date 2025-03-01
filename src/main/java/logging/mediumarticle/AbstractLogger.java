package logging.mediumarticle;

// Abstract class to define the chain of responsibility
public abstract class AbstractLogger {

    protected LoggerLevel level;
    private AbstractLogger nextLogger;

    public AbstractLogger(LoggerLevel level) {
        this.level = level;
    }

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(LoggerLevel level, String msg) {
        if (this.level.equals(level)) {
            display(msg);
        } else if (nextLogger != null) {
            nextLogger.logMessage(level, msg);
        }
    }

    protected abstract void display(String msg);
}
