package fr.imt.parser;

import fr.imt.logger.Color;
import fr.imt.logger.Logger;

import java.util.Scanner;

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

            // TODO: refactor by creating a command handler service
            if (line.equals(":q")) {
                break;
            } else if (line.equals(":log")) {
                this.showLog = !this.showLog;
                System.out.println(Color.grey("Log " + (this.showLog ? "enabled" : "disabled")));
            } else if (line.equals(":h")) {
                showHelp();
            } else {
                process(line);
            }
        }

        System.out.println(Color.grey("Bye!"));
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
