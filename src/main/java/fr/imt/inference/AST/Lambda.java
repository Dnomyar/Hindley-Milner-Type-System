package fr.imt.inference.AST;

import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariableProvider;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class Lambda implements Expression {

    public final Variable identifier;
    public final Expression body;
    private final Logger logger = new Logger();
    private FreshVariableProvider freshVariableProvider;

    public Lambda(Variable identifier, Expression body, FreshVariableProvider freshVariableProvider) {
        this.identifier = identifier;
        this.body = body;
        this.freshVariableProvider = freshVariableProvider;
    }

    @Override
    public Type infer(Environment env) {
        logger.debug("Current exp " + this.toString());

        TypeVariable resultType = this.freshVariableProvider.provideFresh();

        env.extend(this.identifier, resultType);

        Type bodyType = this.body.infer(env);

        logger.debug("Type for body `" + this.body + "` is " + bodyType);

        env.remove(this.identifier);

        return new ArrowType(resultType, bodyType);
    }


    @Override
    public String toString() {
        return "(\\" + this.identifier + " -> " + this.body + ")";
    }

}
