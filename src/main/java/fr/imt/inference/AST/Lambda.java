package fr.imt.inference.AST;

import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariableProvider;
import fr.imt.inference.type.TypeVariable;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;

public class Lambda implements Expression {

    private Logger logger = new Logger(getClass());

    public final Variable identifier;
    public final Expression body;

    public Lambda(Variable identifier, Expression body) {
        this.identifier = identifier;
        this.body = body;
    }

    @Override
    public Type infer(Environment env) {
        logger.debug("Current exp " + this.toString());

        TypeVariable resultType = FreshVariableProvider.getInstance().provideFresh();

        env.extend(this.identifier, resultType);

        Type bodyType = this.body.infer(env);

        env.remove(this.identifier);

        return new ArrowType(resultType, bodyType);
    }


    @Override
    public String toString() {
        return "(\\" + this.identifier + " -> " + this.body + ")";
    }

}
