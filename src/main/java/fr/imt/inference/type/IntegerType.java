package fr.imt.inference.type;

import fr.imt.inference.Substitution;

import java.util.HashSet;
import java.util.Set;

public class IntegerType extends TypeLiteral {
    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        return new HashSet<>();
    }

    @Override
    public boolean isArrow() {
        return false;
    }

    @Override
    public Type applySubstitution(Substitution substitutions) {
        return this;
    }
}
