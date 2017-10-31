package fr.imt.inference;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

import java.util.HashMap;
import java.util.Map;

public class Substitution {

    private Map<TypeVariable, Type> substitutions;

    public Substitution() {
        this.substitutions = new HashMap<>();
    }

    public Substitution add(TypeVariable typeVariable, Type type) {
        this.substitutions.put(typeVariable, type);
        return this;
    }

    public Substitution concat(Substitution substitutions) {
        HashMap<TypeVariable, Type> subst = new HashMap<>();

        subst.putAll(this.substitutions);
        subst.putAll(substitutions.substitutions);

        return substitutions;
    }
}
