package fr.imt.inference.AST;

public class Lambda implements Expression {
    public final Variable identifier;
    public final Expression body;

    public Lambda(Variable identifier, Expression body) {
        this.identifier = identifier;
        this.body = body;
    }
}
