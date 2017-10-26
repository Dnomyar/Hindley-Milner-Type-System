package fr.imt.inference.AST;

public class Let implements Expression {
    public final Variable identifier;
    public final Expression definition;
    public final Expression body;

    public Let(Variable identifier, Expression definition, Expression body) {
        this.identifier = identifier;
        this.definition = definition;
        this.body = body;
    }
}
