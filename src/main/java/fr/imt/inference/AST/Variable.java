package fr.imt.inference.AST;

public class Variable implements Expression {
    public final String name;

    public Variable(String name) {
        this.name = name;
    }
}
