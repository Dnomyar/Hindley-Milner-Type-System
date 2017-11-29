package fr.imt.logger;

import io.vavr.collection.List;

import java.util.function.IntPredicate;

public class Logger {

    public static final Logger instance = new Logger();

    private StringBuilder log = new StringBuilder();
    private String className;

    private Logger() {
        this.className = this.prettyClassName(new Exception().getStackTrace()[1].getClassName());
        this.log = new StringBuilder();
    }

    private String prettyClassName(String className) {
        List<String> exploded = List.of(className.split("\\."));

        final IntPredicate isLastElement = (int i) -> i == exploded.size() - 1;

        final List<ClassNameElement> classNameElementStream = List
                .range(0, exploded.size())
                .map(element -> new ClassNameElement(isLastElement.test(element), exploded.get(element)));

        List<String> readyToPrintClassNameParts = classNameElementStream.map(ClassNameElement::getPrettyValue);

        return readyToPrintClassNameParts.mkString(".");
    }

    private void printMessage(String level, String color, String message) {
        String spaces = List.range(0, 28 - this.className.length()).map(e -> " ").mkString("");

        log.append(String.format("%s%5s%s %s[%s]%s %s %s \n", color, level, Color.ANSI_RESET, Color.ANSI_BLACK_BOLD_BRIGHT, this.className, Color.ANSI_RESET, spaces, message));
    }

    public void debug(String msg) {
        this.printMessage("DEBUG", Color.ANSI_GREEN, msg);
    }

    public void trace(String msg) {
        this.printMessage("TRACE", Color.ANSI_BLUE, msg);
    }

    public void reset() {
        this.log = new StringBuilder();
    }

    @Override
    public String toString() {
        return log.toString();
    }

    private class ClassNameElement {
        public final boolean isLastElement;
        public final String element;

        public ClassNameElement(boolean isLastElement, String element) {

            this.isLastElement = isLastElement;
            this.element = element;
        }

        public String getPrettyValue() {
            return this.isLastElement ? this.element : String.valueOf(this.element.charAt(0));
        }
    }
}
