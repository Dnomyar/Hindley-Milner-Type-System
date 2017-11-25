package fr.imt.logger;

public class Color {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public static String red(String message) {
        return String.format("%s%s%s", ANSI_RED, message, ANSI_RESET);
    }
}
