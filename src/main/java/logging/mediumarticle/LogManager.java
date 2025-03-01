package logging.mediumarticle;

// LogManager to create the chain of responsibility
public class LogManager {

    public static AbstractLogger createChainOfLoggers(LogAppender logAppender, LoggerLevel loggerLevel) {
        AbstractLogger traceLogger = new TraceLogger(LoggerLevel.TRACE, logAppender);
        AbstractLogger debugLogger = new DebugLogger(LoggerLevel.DEBUG, logAppender);
        AbstractLogger infoLogger = new InfoLogger(LoggerLevel.INFO, logAppender);
        AbstractLogger warnLogger = new WarnLogger(LoggerLevel.WARN, logAppender);
        AbstractLogger errorLogger = new ErrorLogger(LoggerLevel.ERROR, logAppender);

        // Setup the chain of responsibility
        traceLogger.setNextLogger(debugLogger);
        debugLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(warnLogger);
        warnLogger.setNextLogger(errorLogger);

        if(loggerLevel == LoggerLevel.TRACE){
            return traceLogger;
        }else if(loggerLevel == LoggerLevel.DEBUG){
            return debugLogger;
        }else if(loggerLevel == LoggerLevel.INFO){
            return infoLogger;
        }else if(loggerLevel == LoggerLevel.WARN) {
            return warnLogger;
        }else{
            return errorLogger;
        }
    }
}
