package fr.imt.inference;

import fr.imt.inference.ast.Expression;
import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.NonexistentVariableException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import fr.imt.logger.Logger;
import io.vavr.control.Either;

public class ExpressionInferer implements Inferable<Expression, Type> {

    private Logger logger = new Logger();

    @Override
    public Either<String, Type> infer(Expression expression) {
        ConstraintCollection constraintCollection = new ConstraintCollection();

        FreshVariable.reset(); // FIXME

        try {
            Type rawReturnType = expression.infer(new Environment(), constraintCollection);

            SubstitutionCollection result = new Unifiyer().runSolve(constraintCollection);

            logger.debug("Constraints generated : " + constraintCollection);

            if (rawReturnType instanceof TypeVariable) {
                return Either.right(result.getOrElse((TypeVariable) rawReturnType, (TypeVariable) rawReturnType));
            }

            return Either.right(rawReturnType);
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
