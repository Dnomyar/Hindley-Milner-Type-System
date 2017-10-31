package fr.imt.inference.AST;

import fr.imt.inference.ConstraintRepository;
import fr.imt.inference.Environment;
import fr.imt.inference.type.Type;

public class Variable implements Expression {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Type infer(Environment env, ConstraintRepository constraintRepository) {
        return env.get(this);
    }


    @Override
    public String toString() {
        return this.name;
    }
}
