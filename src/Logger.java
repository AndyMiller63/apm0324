import org.slf4j.LoggerFactory;

public class Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RentalServiceApp.class);

    public static void error(String errMsg) {
        logger.error(errMsg);
    }
    public static void debug(String debugMsg) {
        logger.debug(debugMsg);
    }
    public static void info(String infoMsg) {
        logger.info(ConsoleColors.CYAN+infoMsg+ConsoleColors.RESET);
    }

    public static void adjustLoggerSettings() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        System.setProperty("org.slf4j.simpleLogger.showDateTime", "true");
        System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "MM/dd/yy' 'HH:mm:ss:SSS");
    }

}
