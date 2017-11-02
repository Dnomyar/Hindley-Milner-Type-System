package fr.imt.inference;

import fr.imt.inference.AST.*;
import fr.imt.inference.logger.Logger;

import static fr.imt.inference.AST.factory.ExpressionFactory.*;

/**
 * @author Clément, Damien, Anaël
 */
public class Main {

    private static Logger logger = new Logger();

    public static void main(String[] args) {
        Variable F = Var("f");
        Variable a = Var("a");
        Variable b = Var("b");
        Variable x = Var("x");

        // (\a b -> b)
        Expression snd = Lamb(a, b, b);

        //(F True)
        Expression FTrue = App(F, Bool(true));

        // (F 1)
        Expression F1 = App(F, Int(1));

        // (\a b -> b) (F True) (F 1)
        Expression application = App(snd, FTrue, F1);


        Expression identityX = Lamb(x, x);
        Expression expression = Let(F, identityX, application);


        logger.debug(expression.toString());
        logger.debug("");

        System.out.println(expression.infer(new Environment()));
    }
}
