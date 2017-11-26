package fr.imt.inference.ast;

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
    public boolean equals(Object o) {
        return o instanceof TBoolean && ((TBoolean) o).value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.value ? "True" : "False";
    }
}
