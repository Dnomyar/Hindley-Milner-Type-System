package fr.imt.inference;

import fr.imt.inference.type.TypeVariable;

public class FreshVariable extends TypeVariable {
    private static Integer counter = 0;

    private FreshVariable(String identifier) {
        super(identifier);
    }

    public FreshVariable() {
        this("t" + counter++);
    }
}
