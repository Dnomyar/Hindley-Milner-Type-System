package fr.imt.inference.ast.binaryexpression.operators;

import io.vavr.collection.List;

public enum EqualityOperator {
    EQUALS("==");

    public final String operator;

    EqualityOperator(String operator) {
        this.operator = operator;
    }

    public static List<EqualityOperator> all() {
        return List.of(EQUALS);
    }

    public Character toChar() {
        return operator.charAt(0);
    }

    @Override
    public String toString() {
        return operator;
    }
}