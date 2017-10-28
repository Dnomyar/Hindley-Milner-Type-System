package fr.imt.inference.logger;

import java.util.Arrays;
import java.util.List;

public class Logger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String ANSI_BLACK_BOLD_BRIGHT = "\033[1;90m";

    private String className;

    public Logger(Class clazz) {
        this.className = prettyClassName(clazz);
    }

    private String prettyClassName(Class clazz) {
        String fullName = clazz.getName();
        List<String> exploded = Arrays.asList(fullName.split("\\."));

        StringBuilder stringBuilder = new StringBuilder();
        int size = exploded.size();
        for (int i = 0; i < size; i++) {
            String element = exploded.get(i);
            if (i < size - 1) {
                stringBuilder.append(element.charAt(0));
                stringBuilder.append('.');
            } else {
                stringBuilder.append(element);
            }
        }
        return stringBuilder.toString();
    }

    private void printMessage(String level, String color, String message) {
        System.out.println(String.format("%s%5s%s %s[%s]%s %s", color, level, ANSI_RESET, ANSI_BLACK_BOLD_BRIGHT, this.className, ANSI_RESET, message));
    }

    public void debug(String msg) {
        this.printMessage("DEBUG", ANSI_GREEN, msg);
    }

    public void trace(String msg) {
        this.printMessage("TRACE", ANSI_BLUE, msg);
    }
}
