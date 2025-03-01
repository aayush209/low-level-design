package logging.shrayanshjain;

// ParkingSystem from Shreyansh Jain, covers chain of responsibility principle only
// No log appenders, file appenders covered
public class Main {

    public static void main(String[] args) {
        LogProcessor logger = new InfoLogProcessor(new DebugLogProcessor(new ErrorLogProcessor(null)));

        logger.log(LogProcessor.INFO, "This is an INFO message");
        logger.log(LogProcessor.DEBUG, "This is a DEBUG message");
        logger.log(LogProcessor.ERROR, "This is a ERROR message");
        logger.log(10, "This is a ERROR message 10"); // won't work as logLevel 10 doesn't exist
    }
}
