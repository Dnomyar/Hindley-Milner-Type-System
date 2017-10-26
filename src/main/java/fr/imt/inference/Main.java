package fr.imt.inference;

import fr.imt.inference.AST.*;
import static fr.imt.inference.AST.ExpressionFactory.*;

/**
 * @author Clément, Damien, Anaël
 */
public class Main {
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

        ExpressionPrinter printer = new ExpressionPrinter();
        System.out.println("ON IMPRIME MAGGLE");
        System.out.println(expression.accept(printer));
    }
}
