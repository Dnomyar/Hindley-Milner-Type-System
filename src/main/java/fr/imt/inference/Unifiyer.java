package fr.imt.inference;

import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class Unifiyer {

    private Logger logger = new Logger(getClass());

    public SubstitutionCollection runSolve(ConstraintRepository constraints) throws InfiniteTypeException, UnificationMismatchException, UnificationFailureException {
        return solve(SubstitutionCollection.empty(), constraints);
    }

    private SubstitutionCollection solve(SubstitutionCollection substitutions, ConstraintRepository constraints) throws UnificationFailureException, InfiniteTypeException, UnificationMismatchException {

        if(constraints.isEmpty()) {
            return substitutions;
        }

        Constraint constraint = constraints.head();

        SubstitutionCollection constraintSubstitutionCollection = unify(constraint);

        SubstitutionCollection headSubstitution = constraintSubstitutionCollection.concat(substitutions);

        logger.debug("Unifying constraint result : " + constraint + " -> " + constraintSubstitutionCollection + " == " + headSubstitution);

        ConstraintRepository tailConstraints = constraints.tail().applySubstitution(headSubstitution);

        return solve(headSubstitution, tailConstraints);
    }

    // TODO refactor (left and right)
    private SubstitutionCollection unify(Constraint constraint) throws InfiniteTypeException, UnificationFailureException, UnificationMismatchException {

        //logger.debug("Unifying constraint  : " + constraint);

        if(constraint.right.equals(constraint.left)){ // TODO implement equals
            return SubstitutionCollection.empty();
        }

        if(constraint.right.isTypeVariable()){
            return bind((TypeVariable) constraint.right, constraint.left);
        }

        if(constraint.left.isTypeVariable()){
            return bind((TypeVariable) constraint.left, constraint.right);
        }

        if(constraint.left.isArrow() && constraint.right.isArrow()){
            ArrowType leftArrowType = (ArrowType) constraint.left;
            ArrowType rightArrowType = (ArrowType) constraint.right;

            TypeList arrowType1 = new TypeList(leftArrowType.left, leftArrowType.right);
            TypeList arrowType2 = new TypeList(rightArrowType.left, rightArrowType.right);

            return unifyMany(arrowType1, arrowType2);
        }

        throw new UnificationFailureException(constraint);
    }


    private SubstitutionCollection unifyMany(TypeList arrowType1, TypeList arrowType2) throws UnificationMismatchException, InfiniteTypeException, UnificationFailureException {
        // both empty
        if(arrowType1.isEmpty() && arrowType2.isEmpty()){
            return SubstitutionCollection.empty();
        }
        // one empty the other is not
        if(arrowType1.isEmpty() || arrowType2.isEmpty()){
            throw new UnificationMismatchException(arrowType1, arrowType2);
        }

        // could not be null because of the check before
        Type headArrowType1 = arrowType1.head();
        Type headArrowType2 = arrowType2.head();

        SubstitutionCollection headSubstitutionCollection = unify(new Constraint(headArrowType1, headArrowType2)); // todo refactor unify

        logger.debug("Unifying constraint result : " + new Constraint(headArrowType1, headArrowType2) + " -> " + headSubstitutionCollection);


        SubstitutionCollection tailSubstitutionCollection = unifyMany(
                arrowType1.applySubstitution(headSubstitutionCollection),
                arrowType2.applySubstitution(headSubstitutionCollection)
        );

        return tailSubstitutionCollection.concat(headSubstitutionCollection);
    }


    private SubstitutionCollection bind(TypeVariable typeVariable, Type type) throws InfiniteTypeException{
        if(type.isTypeVariable() && typeVariable.equals(type)){
            return SubstitutionCollection.empty();
        }
        if(isPartOfFreeVariables(typeVariable, type)){
            throw new InfiniteTypeException(typeVariable, type);
        }
        return new SubstitutionCollection(typeVariable, type);
    }

    private boolean isPartOfFreeVariables(TypeVariable typeVariable, Type type) {
        return type.getFreeTypeVariables().contains(typeVariable);
    }


}
