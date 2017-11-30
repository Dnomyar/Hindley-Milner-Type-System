package fr.imt.inference;

import fr.imt.inference.ast.Expression;
import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.NonexistentVariableException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.type.Type;
import fr.imt.logger.Logger;
import fr.imt.logger.LoggerFactory;
import io.vavr.control.Either;

public class ExpressionInferer implements Inferable<Expression, Type> {

    private Logger logger = LoggerFactory.getConsoleLogger();

    @Override
    public Either<String, Type> infer(Expression expression) {
        ConstraintCollection constraintCollection = new ConstraintCollection();

        FreshVariable.reset(); // FIXME

        try {
            Type rawReturnType = expression.infer(new Environment(), constraintCollection);

            SubstitutionCollection result = new Unifiyer().runSolve(constraintCollection);

            logger.debug("Constraints generated : " + constraintCollection);

            return Either.right(rawReturnType.applySubstitution(result));
        } catch (
            UnificationMismatchException |
            UnificationFailureException |
            InfiniteTypeException |
            NonexistentVariableException e
        ) {
            return Either.left(e.getMessage());
        }

    }

}
