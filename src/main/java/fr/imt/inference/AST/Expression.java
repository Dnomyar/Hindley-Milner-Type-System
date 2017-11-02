package fr.imt.inference.AST;

import fr.imt.inference.Environment;
import fr.imt.inference.type.Type;

public interface Expression {
    Type infer(Environment env);
}
