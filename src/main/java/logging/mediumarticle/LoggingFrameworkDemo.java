package logging.mediumarticle;

// Demo class
public class LoggingFrameworkDemo {

    public static void main(String[] args) {
        Logger logger = Logger.getInstance();

        // Logging with default configuration
        LogAppender consoleAppender = new ConsoleAppender();
        LoggerConfig config = new LoggerConfig(LoggerLevel.ERROR, consoleAppender);
        logger.setConfig(config);

        logger.trace("This is a trace message");
        logger.debug("This is a debug message");
        logger.info("This is an information message");
        logger.warn("This is a debug message");
        logger.error("This is an error message");

        // Changing log level and appender to FileAppender
        LogAppender fileAppender = new FileAppender("app.log");
        LoggerConfig fileConfig = new LoggerConfig(LoggerLevel.INFO, fileAppender);
        logger.setConfig(fileConfig);

        logger.info("This is an information message");
        logger.warn("This is a debug message");
        logger.error("This is an error message");
    }
}
