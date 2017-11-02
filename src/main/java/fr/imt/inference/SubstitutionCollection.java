package fr.imt.inference;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

public class SubstitutionCollection {

    private Map<TypeVariable, Type> substitutions;

    public SubstitutionCollection() {
        this.substitutions = HashMap.empty();
    }

    public SubstitutionCollection(Map<TypeVariable, Type> substitutions) {
        this.substitutions = substitutions;
    }

    public SubstitutionCollection(TypeVariable typeVariable, Type type) {
        this.substitutions = HashMap.of(typeVariable, type);
    }

    public SubstitutionCollection concat(SubstitutionCollection substitutions) {
        return new SubstitutionCollection(this.substitutions.merge(substitutions.substitutions));
    }

    public Type getOrElse(TypeVariable key, TypeVariable defaultValue) {
        return this.substitutions.getOrElse(key, defaultValue);
    }

    @Override
    public String toString() {
        return substitutions.toString();
    }
}
