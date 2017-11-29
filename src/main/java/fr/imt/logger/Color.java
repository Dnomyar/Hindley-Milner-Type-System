package fr.imt.logger;

public class Color {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BOLD_BRIGHT = "\033[1;90m";

    private static String format(String color, String message) {
        return String.format("%s%s%s", color, message, ANSI_RESET);
    }

    public static String red(String message) {
        return format(ANSI_RED, message);
    }

    public static String grey(String message) {
        return format(ANSI_BLACK_BOLD_BRIGHT, message);
    }

    public static String green(String message) {
        return format(ANSI_GREEN, message);
    }

    public static String purple(String message) {
        return format(ANSI_PURPLE, message);
    }
}
