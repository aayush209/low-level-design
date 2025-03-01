package logging.mediumarticle;

public class DebugLogger extends AbstractLogger {

    private final LogAppender logAppender;

    public DebugLogger(LoggerLevel level, LogAppender logAppender) {
        super(level);
        this.logAppender = logAppender;
    }

    @Override
    protected void display(String msg) {
        logAppender.append(new LogMessage(level, msg));
    }
}
