package logging.mediumarticle;

// LoggerConfig public class to configure logging settings
public class LoggerConfig {

    private LoggerLevel loggerLevel;
    private LogAppender logAppender;

    public LoggerConfig(LoggerLevel loggerLevel, LogAppender logAppender) {
        this.loggerLevel = loggerLevel;
        this.logAppender = logAppender;
    }

    public LoggerLevel getLogLevel() {
        return loggerLevel;
    }

    public void setLogLevel(LoggerLevel loggerLevel) {
        this.loggerLevel = loggerLevel;
    }

    public LogAppender getLogAppender() {
        return logAppender;
    }

    public void setLogAppender(LogAppender logAppender) {
        this.logAppender = logAppender;
    }
}
