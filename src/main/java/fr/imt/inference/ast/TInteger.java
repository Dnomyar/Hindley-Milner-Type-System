package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.IntegerType;
import fr.imt.inference.type.Type;

public class TInteger implements Literal {
    public final Integer value;

    public TInteger(Integer value) {
        this.value = value;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        return new IntegerType();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TInteger && ((TInteger) o).value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
