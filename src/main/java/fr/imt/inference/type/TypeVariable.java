package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;

import java.util.Set;

public class TypeVariable implements Type {
    private String identifier;

    public TypeVariable(String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean isTypeVariable() {
        return true;
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public boolean isArrow() {
        return false;
    }

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
