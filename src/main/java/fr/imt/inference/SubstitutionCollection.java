package fr.imt.inference;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

public class SubstitutionCollection implements Substituable<SubstitutionCollection> {

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
        return new SubstitutionCollection(
                substitutions.applySubstitution(this).substitutions
                        .merge(this.substitutions));
    }

    public Type getOrElse(TypeVariable key, TypeVariable defaultValue) {
        return this.substitutions.getOrElse(key, defaultValue);
    }

    @Override
    public String toString() {
        return substitutions.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubstitutionCollection that = (SubstitutionCollection) o;

        return substitutions != null ? substitutions.equals(that.substitutions) : that.substitutions == null;
    }

    @Override
    public int hashCode() {
        return substitutions != null ? substitutions.hashCode() : 0;
    }

    @Override
    public SubstitutionCollection applySubstitution(SubstitutionCollection substitutions) {
        Map<TypeVariable, Type> substitued =
                this.substitutions.mapValues(type -> type.applySubstitution(substitutions));
        return new SubstitutionCollection(substitued);
    }
}
