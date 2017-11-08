package fr.imt.inference.type;

import fr.imt.inference.Environment;
import fr.imt.inference.FreeTypeVariableContainer;
import fr.imt.inference.FreshVariable;
import fr.imt.inference.SubstitutionCollection;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

import java.util.function.Function;

public class Scheme implements FreeTypeVariableContainer, TypeInstantiator {

    private Set<TypeVariable> genericTypeVariables;
    private Type type;

    public Scheme(Set<TypeVariable> genericTypeVariables, Type type) {
        this.genericTypeVariables = genericTypeVariables;
        this.type = type;
    }

    public Scheme(TypeVariable type) {
        this(HashSet.empty(), type);
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        return type.getFreeTypeVariables().diff(genericTypeVariables);
    }

    @Override
    public Type instantiate(Environment environment) {
        SubstitutionCollection substitutionCollection =
                new SubstitutionCollection(
                        genericTypeVariables
                                .toMap(Function.identity(), v -> new FreshVariable())
                );
        return this.type.applySubstitution(substitutionCollection);
    }

    @Override
    public String toString() {
        return "∀ " + genericTypeVariables.mkString(", ") + " => " + type;
    }
}
