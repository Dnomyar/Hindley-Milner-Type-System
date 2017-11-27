package fr.imt.inference.ast;

import io.vavr.collection.List;

public enum Operator {
    PLUS("+"), MINUS("-"), TIME("*"), DIVIDE("/");

    public final String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public static List<Operator> all() {
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