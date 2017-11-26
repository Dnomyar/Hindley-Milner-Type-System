package fr.imt.inference.ast;

public enum Operator {
    PLUS("+"), MINUS("-"), TIME("*"), DIVIDE("/");

    public final String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }
}