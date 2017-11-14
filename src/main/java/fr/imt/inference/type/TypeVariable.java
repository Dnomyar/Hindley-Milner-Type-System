package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;


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
        return HashSet.of(this);
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
