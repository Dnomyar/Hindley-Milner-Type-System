package fr.imt.inference;

import fr.imt.inference.type.Type;
import io.vavr.collection.List;

// needs a iface ?
public class TypeCollection {

    private List<Type> types;

    public TypeCollection(Type... types) {
        this.types = List.of(types);
    }

    public TypeCollection(List<Type> types) {
        this.types = types;
    }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public Type popHead() {
        Type head = types.head();

        this.types = types.tail();

        return head;
    }

    public TypeCollection applySubstitution(SubstitutionCollection substitutions) {
        return new TypeCollection(this.types.map(type -> type.applySubstitution(substitutions)));
    }
}
