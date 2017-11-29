package fr.imt.parser;

import fr.imt.logger.Color;
import fr.imt.logger.Logger;
import io.vavr.API;

import java.util.Scanner;

import static io.vavr.API.*;

public class Repl<T> {

    private final Parsable<T> parser;
    private final ReplProcessable<T> processor;
    private boolean showLog;

    public Repl(Parsable<T> parser, ReplProcessable<T> processor) {
        this.parser = parser;
        this.processor = processor;
        this.showLog = true;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hindley Milner Type System");
        showHelp();

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            Match(line).of(
                Case($(":q"), o -> API.run(this::endProgram)),
                Case($(":log"), o -> API.run(this::toggleLog)),
                Case($(":h"), o -> API.run(this::showHelp)),
                Case($(), o -> API.run(() -> this.process(line)))
            );
        }

    }

    private void endProgram() {
        System.out.println(Color.grey("Bye!"));
        System.exit(0);
    }

    private void toggleLog() {
        this.showLog = !this.showLog;
        System.out.println(Color.grey("Log " + (this.showLog ? "enabled" : "disabled")));
    }

    private void process(String line) {
        String result = parser.parse(line)
            .fold(
                parseError -> Color.red("Parsing error : " + parseError),
                processor::process
            );

        if (this.showLog) System.out.print(Logger.instance);

        System.out.println(Color.purple(result));
    }

    private void showHelp() {
        System.out.println("\nCommands:");
        System.out.println("\t\t- " + Color.green(":log") + "\t: enable/disable logs");
        System.out.println("\t\t- " + Color.green(":h") + "\t: help");
        System.out.println("\t\t- " + Color.green(":q") + "\t: quit");
        System.out.println();
    }

}
