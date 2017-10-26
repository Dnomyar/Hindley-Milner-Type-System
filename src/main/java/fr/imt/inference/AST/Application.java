package fr.imt.inference.AST;

public class Application implements Expression {
    public final Expression body;
    public final Expression argument;

    public Application(Expression body, Expression argument) {
        this.body = body;
        this.argument = argument;
    }
}
