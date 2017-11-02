package fr.imt.inference.AST;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.BooleanType;
import fr.imt.inference.type.Type;

public class TBoolean implements Literal {
    public final Boolean value;

    public TBoolean(Boolean value) {
        this.value = value;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        return new BooleanType();
    }

    @Override
    public String toString() {
        return this.value ? "True" : "False";
    }
}
