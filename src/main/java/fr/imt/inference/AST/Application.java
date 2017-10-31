package fr.imt.inference.AST;

import fr.imt.inference.ConstraintRepository;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariableProvider;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class Application implements Expression {

    public final Expression body;
    public final Expression argument;
    private final Logger logger = new Logger();
    private ConstraintRepository constraintRepository;
    private FreshVariableProvider freshVariableProvider;

    public Application(
            Expression body,
            Expression argument,
            ConstraintRepository constraintRepository,
            FreshVariableProvider freshVariableProvider
    ) {
        this.body = body;
        this.argument = argument;
        this.constraintRepository = constraintRepository;
        this.freshVariableProvider = freshVariableProvider;
    }

    @Override
    public Type infer(Environment env, ConstraintRepository constraintRepository) {
        logger.debug("Current exp " + this.toString());

        Type bodyType = this.body.infer(env, constraintRepository);

        logger.debug("Type for body `" + this.body + "` is " + bodyType);

        Type argumentType = this.argument.infer(env, constraintRepository);

        logger.debug("Type for argument `" + this.argument + "` is " + argumentType);

        TypeVariable returnType = this.freshVariableProvider.provideFresh();

        this.constraintRepository.uni(bodyType, new ArrowType(argumentType, returnType));

        return returnType;
    }

    @Override
    public String toString() {
        return "(" + this.body + " " + this.argument + ")";
    }
}
