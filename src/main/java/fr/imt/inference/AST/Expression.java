package fr.imt.inference.AST;

import fr.imt.inference.ExpressionPrinter;

public interface Expression {
    public default String accept(ExpressionPrinter printer) {
        return printer.visit(this);
    }
}
