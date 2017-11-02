package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;

import java.util.HashSet;
import java.util.Set;

public abstract class TypeLiteral implements Type {

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        return new HashSet<>();
    }

    @Override
    public Boolean containsTheFreeVariable(TypeVariable freeTypeVariable) {
        return false;
    }

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        return this;
    }
}
