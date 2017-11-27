package fr.imt.parser;

import fr.imt.logger.Color;

import java.util.Scanner;

public class Repl<T> {

    private final Parsable<T> parser;
    private final ReplProcessable<T> processor;

    public Repl(Parsable<T> parser, ReplProcessable<T> processor) {
        this.parser = parser;
        this.processor = processor;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hindley Milner Type System");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            // TODO: implement a set of commands
            if (line.equals(":q")) {
                break;
            }

            String res = parser.parse(line)
                .fold(
                    parseError -> Color.red("Parsing error : " + parseError),
                    processor::process
                );

            System.out.println(res);
        }

        System.out.println("Bye!");
    }

}
