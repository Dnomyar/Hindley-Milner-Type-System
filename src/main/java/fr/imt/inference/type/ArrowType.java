package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;
import io.vavr.collection.Set;


public class ArrowType extends Generalizable implements Type {
    public final Type left;
    public final Type right;

    public ArrowType(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        Set<TypeVariable> leftFTV = left.getFreeTypeVariables();
        Set<TypeVariable> rightFTV = right.getFreeTypeVariables();

        Set<TypeVariable> result = leftFTV;
        result.addAll(rightFTV);

        return result;
    }

    @Override
    public Boolean containsTheFreeVariable(TypeVariable freeTypeVariable) {
        return this.getFreeTypeVariables().contains(freeTypeVariable);
    }

    @Override
    public boolean isArrow() {
        return true;
    }

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        Type newLeft = left.applySubstitution(substitutions);
        Type newRight = right.applySubstitution(substitutions);

        return new ArrowType(newLeft, newRight);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArrowType
                && ((ArrowType) o).left.equals(this.left)
                && ((ArrowType) o).right.equals(this.right);
    }

    @Override
    public String toString() {
        return this.left + " -> " + this.right;
    }

}
