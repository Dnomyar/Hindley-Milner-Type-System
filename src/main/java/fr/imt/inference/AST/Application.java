package fr.imt.inference.AST;

import fr.imt.inference.ConstraintRepository;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariableProvider;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.TypeVariable;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;

public class Application implements Expression {

    private Logger logger = new Logger();

    public final Expression body;
    public final Expression argument;

    public Application(Expression body, Expression argument) {
        this.body = body;
        this.argument = argument;
    }

    @Override
    public Type infer(Environment env) {
        logger.debug("Current exp " + this.toString());

        Type bodyType = this.body.infer(env);

        logger.debug("Type for body `" + this.body + "` is " + bodyType);

        Type argumentType = this.argument.infer(env);

        logger.debug("Type for argument `" + this.argument + "` is " + argumentType);

        TypeVariable returnType = FreshVariableProvider.getInstance().provideFresh();

        ConstraintRepository.getInstance().uni(bodyType, new ArrowType(argumentType, returnType));

        return returnType;
    }

    @Override
    public String toString() {
        return this.body + " " + this.argument;
    }
}
