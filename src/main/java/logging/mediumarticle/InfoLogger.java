package logging.mediumarticle;

// Concrete loggers for different levels
public class InfoLogger extends AbstractLogger {

    private final LogAppender logAppender;

    public InfoLogger(LoggerLevel level, LogAppender logAppender) {
        super(level);
        this.logAppender = logAppender;
    }

    @Override
    protected void display(String msg) {
        logAppender.append(new LogMessage(level, msg));
    }
}
