package fr.imt.inference;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.imt.inference.AST.Expression;
import fr.imt.inference.AST.Variable;
import fr.imt.inference.AST.factory.ExpressionFactory;
import fr.imt.inference.di.AppInjector;
import fr.imt.inference.AST.Expression;
import fr.imt.inference.AST.Variable;
import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

/**
 * @author Clément, Damien, Anaël
 */
public class Main {

    private final static Logger logger = new Logger();

    public static void main(String[] args) {
Injector injector = Guice.createInjector(new AppInjector());

        ExpressionFactory ef = injector.getInstance(ExpressionFactory.class);        Variable F = ef.Var("f");
        Variable a = ef.Var("a");
        Variable b = ef.Var("b");
        Variable x = ef.Var("x");

        // (\a b -> b)
        Expression snd = ef.Lamb(a, b, b);

        //(F True)
        Expression FTrue = ef.App(F, ef.Bool(true));

        // (F 1)
        Expression F1 = ef.App(F, ef.Int(1));

        // (\a b -> b) (F True) (F 1)
        Expression application = ef.App(snd, FTrue, F1);


        Expression identityX = ef.Lamb(x, x);
        Expression expression = ef.Let(F, identityX, application);

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
