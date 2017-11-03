package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;


public abstract class TypeLiteral implements Type {

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        return HashSet.empty();
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
