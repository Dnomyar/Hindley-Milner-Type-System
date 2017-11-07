package fr.imt.inference;

import fr.imt.inference.AST.Expression;
import fr.imt.inference.AST.Variable;
import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

import static fr.imt.inference.AST.factory.ExpressionFactory.*;

/**
 * @author Clément, Damien, Anaël
 */
public class Main {

    private final static Logger logger = new Logger();

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

        ConstraintCollection constraintCollection = new ConstraintCollection();

        Type rawReturnType = expression.infer(new Environment(), constraintCollection);
        System.out.println(rawReturnType);

        System.out.println("Inference Finished");

        System.out.println(constraintCollection);
        try {
            SubstitutionCollection result = new Unifiyer().runSolve(constraintCollection);
            System.out.println(result);
            if(rawReturnType instanceof TypeVariable){
                System.out.println("Expression type : " + result.getOrElse((TypeVariable) rawReturnType, (TypeVariable) rawReturnType));
            }else {
                System.out.println("Expression type : " + rawReturnType);
            }
        } catch (InfiniteTypeException e) {
            e.printStackTrace();
        } catch (UnificationMismatchException e) {
            e.printStackTrace();
        } catch (UnificationFailureException e) {
            e.printStackTrace();
        }
    }
}
