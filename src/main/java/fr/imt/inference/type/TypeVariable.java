package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;

import java.util.HashSet;
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
        Set<TypeVariable> result = new HashSet<>();
        result.add(this);
        return result;
    }

    @Override
    public Boolean containsTheFreeVariable(TypeVariable freeTypeVariable) {
        return this.getFreeTypeVariables().contains(freeTypeVariable);
    }

    @Override
    public boolean isArrow() {
        return false;
    }

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        return substitutions.getOrElse(this, this);
    }

}
