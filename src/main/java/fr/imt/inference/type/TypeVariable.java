package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;


public class TypeVariable extends Generalizable implements Type {
    public final String identifier;

    public TypeVariable(String identifier) {
        this.identifier = identifier;
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

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeVariable && ((TypeVariable) o).identifier.equals(this.identifier);
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }

    @Override
    public String toString() {
        return identifier;
    }

}
