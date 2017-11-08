package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.Type;

public interface Expression {
    Type infer(Environment env, ConstraintCollection constraintCollection);
}
