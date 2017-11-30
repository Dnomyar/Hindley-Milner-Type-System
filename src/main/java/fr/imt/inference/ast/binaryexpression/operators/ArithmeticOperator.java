package fr.imt.inference.ast.binaryexpression.operators;

import io.vavr.collection.List;

public enum ArithmeticOperator {
    PLUS("+"), MINUS("-"), TIME("*"), DIVIDE("/");

    public final String operator;

    ArithmeticOperator(String operator) {
        this.operator = operator;
    }

    public static List<ArithmeticOperator> all() {
        return List.of(PLUS, MINUS, TIME, DIVIDE);
    }

    public Character toChar() {
        return operator.charAt(0);
    }

    @Override
    public String toString() {
        return operator;
    }
}