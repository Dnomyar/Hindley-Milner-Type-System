package fr.imt.logger;

public class LoggerFactory {

    private static final Console console = new Console();

    public static Logger getConsoleLogger() {
        return console;
    }

}
