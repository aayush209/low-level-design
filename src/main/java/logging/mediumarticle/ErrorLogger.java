package logging.mediumarticle;

public class ErrorLogger extends AbstractLogger {

    private final LogAppender logAppender;

    public ErrorLogger(LoggerLevel level, LogAppender logAppender) {
        super(level);
        this.logAppender = logAppender;
    }

    @Override
    protected void display(String msg) {
        logAppender.append(new LogMessage(level, msg));
    }
}
