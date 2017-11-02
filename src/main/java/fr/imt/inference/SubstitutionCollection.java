package fr.imt.inference;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

import java.util.HashMap;
import java.util.Map;

public class SubstitutionCollection {

    private Map<TypeVariable, Type> substitutions;

    public SubstitutionCollection() {
        this.substitutions = new HashMap<>();
    }

    public SubstitutionCollection(Map<TypeVariable, Type> substitutions) {
        this.substitutions = substitutions;
    }

    public SubstitutionCollection(TypeVariable typeVariable, Type type) {
        this.substitutions = new HashMap<>();
        this.substitutions.put(typeVariable, type);
    }

    public static SubstitutionCollection empty() {
        return new SubstitutionCollection();
    }

    public SubstitutionCollection add(TypeVariable typeVariable, Type type) {
        this.substitutions.put(typeVariable, type);
        return this;
    }

    public SubstitutionCollection concat(SubstitutionCollection substitutions) {
        HashMap<TypeVariable, Type> subst = new HashMap<>();

        subst.putAll(this.substitutions);
        subst.putAll(substitutions.substitutions);

        return new SubstitutionCollection(subst);
    }

    public Type getOrElse(TypeVariable key, TypeVariable defaultValue) {
        return this.substitutions.getOrDefault(key, defaultValue);
    }

    @Override
    public String toString() {
        return substitutions.toString();
    }
}
