package logging.mediumarticle;

// Logger public class using the chain of responsibility
public class Logger {

    private static Logger loggerInstance;
    private static AbstractLogger chainOfLoggers;
    private static LoggerConfig loggerConfig;

    private Logger() {
        if (loggerInstance != null) {
            throw new IllegalStateException("Logger instance already created");
        }
    }

    public static synchronized Logger getInstance() {
        if (loggerInstance == null) {
            synchronized (Logger.class){
                if (loggerInstance == null) {
                    loggerInstance = new Logger();
                }
            }
        }
        return loggerInstance;
    }

    public void setConfig(LoggerConfig config) {
        loggerConfig = config;
        chainOfLoggers = LogManager.createChainOfLoggers(config.getLogAppender(), loggerConfig.getLogLevel());
    }

    public void trace(String message) {
        createLog(LoggerLevel.TRACE, message);
    }

    public void debug(String message) {
        createLog(LoggerLevel.DEBUG, message);
    }

    public void info(String message) {
        createLog(LoggerLevel.INFO, message);
    }

    public void warn(String message) {
        createLog(LoggerLevel.WARN, message);
    }

    public void error(String message) {
        createLog(LoggerLevel.ERROR, message);
    }


    private void createLog(LoggerLevel level, String message) {
        if (chainOfLoggers != null) {
            chainOfLoggers.logMessage(level, message);
        }
    }
}
