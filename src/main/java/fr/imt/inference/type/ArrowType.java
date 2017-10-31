package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;

import java.util.Set;

public class ArrowType implements Type {
    public final Type left;
    public final Type right;

    public ArrowType(Type left, Type right) {

        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.left + " -> " + this.right;
    }

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public boolean isArrow() {
        return true;
    }

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }
}
