package logging.mediumarticle;

// ConsoleAppender that prints logs to the console
public class ConsoleAppender implements LogAppender {

    @Override
    public void append(LogMessage logMessage) {
        System.out.println(logMessage);
    }
}
