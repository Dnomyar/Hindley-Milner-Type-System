package fr.imt.inference.type;

import fr.imt.inference.type.Type;

public class TypeVariable implements Type {
    private String identifier;

    public TypeVariable(String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toString() {
        return identifier;
    }
}
