package fr.imt;

import fr.imt.inference.ExpressionInferer;
import fr.imt.parser.ExpressionParser;
import fr.imt.parser.InferenceProcessor;
import fr.imt.parser.Repl;

/**
 * @author Clément, Damien, Anaël
 */
public class Main {

    public static void main(String[] args) {
        new Repl<>(new ExpressionParser(), new InferenceProcessor<>(new ExpressionInferer())).run();
    }

}
