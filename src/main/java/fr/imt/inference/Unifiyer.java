package fr.imt.inference;

import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import fr.imt.logger.Logger;

public class Unifiyer {

    private Logger logger = Logger.instance;

    public SubstitutionCollection runSolve(ConstraintCollection constraints) throws InfiniteTypeException, UnificationMismatchException, UnificationFailureException {
        return solve(new SubstitutionCollection(), constraints);
    }

    private SubstitutionCollection solve(SubstitutionCollection substitutions, ConstraintCollection constraints) throws UnificationFailureException, InfiniteTypeException, UnificationMismatchException {
        if (constraints.isEmpty()) {
            return substitutions;
        }

        Constraint constraint = constraints.head();

        SubstitutionCollection constraintSubstitutionCollection = unify(constraint);

        SubstitutionCollection headSubstitution = constraintSubstitutionCollection.concat(substitutions);

        logger.debug("Unifying constraint result : " + constraint + " -> " + constraintSubstitutionCollection + " == " + headSubstitution);

        ConstraintCollection tailConstraints = constraints.tail().applySubstitution(headSubstitution);

        return solve(headSubstitution, tailConstraints);
    }

    // TODO refactor (left and right)
    private SubstitutionCollection unify(Constraint constraint) throws InfiniteTypeException, UnificationFailureException, UnificationMismatchException {

        //logger.debug("Unifying constraint  : " + constraint);

        if (constraint.right.equals(constraint.left)) {
            return new SubstitutionCollection();
        }

        if (constraint.right.isTypeVariable()) {
            return bind((TypeVariable) constraint.right, constraint.left);
        }

        if (constraint.left.isTypeVariable()) {
            return bind((TypeVariable) constraint.left, constraint.right);
        }

        if (constraint.left.isArrow() && constraint.right.isArrow()) {
            ArrowType leftArrowType = (ArrowType) constraint.left;
            ArrowType rightArrowType = (ArrowType) constraint.right;

            TypeCollection arrowType1 = new TypeCollection(leftArrowType.left, leftArrowType.right);
            TypeCollection arrowType2 = new TypeCollection(rightArrowType.left, rightArrowType.right);

            return unifyMany(arrowType1, arrowType2);
        }

        throw new UnificationFailureException(constraint);
    }


    private SubstitutionCollection unifyMany(TypeCollection arrowType1, TypeCollection arrowType2) throws UnificationMismatchException, InfiniteTypeException, UnificationFailureException {
        // both empty
        if (arrowType1.isEmpty() && arrowType2.isEmpty()) {
            return new SubstitutionCollection();
        }
        // one empty the other is not
        if (arrowType1.isEmpty() || arrowType2.isEmpty()) {
            throw new UnificationMismatchException(arrowType1, arrowType2);
        }

        // could not be null because of the check before
        Type headArrowType1 = arrowType1.popHead();
        Type headArrowType2 = arrowType2.popHead();

        SubstitutionCollection headSubstitutionCollection = unify(new Constraint(headArrowType1, headArrowType2)); // todo refactor unify

        logger.debug("Unifying constraint result : " + new Constraint(headArrowType1, headArrowType2) + " -> " + headSubstitutionCollection);


        SubstitutionCollection tailSubstitutionCollection = unifyMany(
                arrowType1.applySubstitution(headSubstitutionCollection),
                arrowType2.applySubstitution(headSubstitutionCollection)
        );

        return tailSubstitutionCollection.concat(headSubstitutionCollection);
    }


    private SubstitutionCollection bind(TypeVariable typeVariable, Type type) throws InfiniteTypeException {
        if (type.isTypeVariable() && typeVariable.equals(type)) {
            return new SubstitutionCollection();
        }
        if (type.containsTheFreeVariable(typeVariable)) {
            throw new InfiniteTypeException(typeVariable, type);
        }

        return new SubstitutionCollection(typeVariable, type);
    }
}
