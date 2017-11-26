package fr.imt.inference.AST;

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
    public String toString() {
        return this.value.toString();
    }
}