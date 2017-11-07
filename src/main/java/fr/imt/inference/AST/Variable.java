package fr.imt.inference.AST;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.Type;

public class Variable implements Expression {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        return env.get(this);
    }

//    @Override
//    public boolean equals(Object o) {
//        return o instanceof Variable && ((Variable) o).name.equals(this.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return name != null ? name.hashCode() : 0;
//    }

    @Override
    public String toString() {
        return this.name;
    }
}
