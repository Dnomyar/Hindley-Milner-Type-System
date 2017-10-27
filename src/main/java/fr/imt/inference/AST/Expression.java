package fr.imt.inference.AST;

import fr.imt.inference.Environment;
import fr.imt.inference.ExpressionPrinter;
import fr.imt.inference.type.Type;

public interface Expression {
    default String accept(ExpressionPrinter printer) {
        return printer.visit(this);
    }

    Type infer(Environment env);
}
