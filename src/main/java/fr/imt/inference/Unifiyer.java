package fr.imt.inference;

import fr.imt.inference.errors.InfiniteTypeException;
import fr.imt.inference.errors.UnificationFailureException;
import fr.imt.inference.errors.UnificationMismatchException;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

import java.util.List;

public class Unifiyer {


    public Substitution runSolve(ConstraintRepository constraints) throws InfiniteTypeException, UnificationMismatchException, UnificationFailureException {
        return solve(new Substitution(), constraints);
    }

    private Substitution solve(Substitution substitutions, ConstraintRepository constraints) throws UnificationFailureException, InfiniteTypeException, UnificationMismatchException {

        if(constraints.isEmpty()) {
            return substitutions;
        }

        Tuple<Type, Type> constraint = constraints.head();

        Substitution constraintSubstitution = unify(constraint);

        Substitution headSubstitution = constraintSubstitution.concat(substitutions);

        ConstraintRepository tailConstraints = constraints.tail().applySubstitution(headSubstitution); // TODO add an iface with method applySubstitution ?

        return solve(headSubstitution, tailConstraints);
    }

    // TODO refactor (left and right)
    private Substitution unify(Tuple<Type, Type> constraint) throws InfiniteTypeException, UnificationFailureException, UnificationMismatchException {
        if(constraint.right.equals(constraint.left)){ // TODO implement equals
            return new Substitution();
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

            TypeList arrowType1 = new TypeList().add(leftArrowType.left, leftArrowType.right);
            TypeList arrowType2 = new TypeList().add(rightArrowType.left, rightArrowType.right);

            return unifyMany(arrowType1, arrowType2);
        }

        throw new UnificationFailureException(constraint);
    }


    private Substitution unifyMany(TypeList arrowType1, TypeList arrowType2) throws UnificationMismatchException, InfiniteTypeException, UnificationFailureException {
        // both empty
        if(arrowType1.isEmpty() && arrowType2.isEmpty()){
            return new Substitution();
        }
        // one empty the other is not
        if(arrowType1.isEmpty() || arrowType2.isEmpty()){
            throw new UnificationMismatchException(arrowType1, arrowType2);
        }

        // could not be null because of the check before
        Type headArrowType1 = arrowType1.head();
        Type headArrowType2 = arrowType2.head();

        Substitution headSubstitution = unify(new Tuple<>(headArrowType1, headArrowType2)); // todo refactor unify

        Substitution tailSubstitution = unifyMany(arrowType1.applySubstitution(headSubstitution), arrowType2.applySubstitution(headSubstitution));

        return tailSubstitution.concat(headSubstitution);
    }


    private Substitution bind(TypeVariable typeVariable, Type type) throws InfiniteTypeException{
        if(type.isTypeVariable() && typeVariable.equals(type)){
            return new Substitution();
        }
        if(isPartOfFreeVariables(typeVariable, type)){
            throw new InfiniteTypeException(typeVariable, type);
        }
        return new Substitution().add(typeVariable, type);
    }

    private boolean isPartOfFreeVariables(TypeVariable typeVariable, Type type) {
        return type.getFreeTypeVariables().contains(typeVariable);
    }


}
