package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariable;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import fr.imt.logger.Logger;

public class Application implements Expression {

    public final Expression body;
    public final Expression argument;

    private final Logger logger = new Logger();

    public Application(
            Expression body,
            Expression argument
    ) {
        this.body = body;
        this.argument = argument;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        logger.debug("Context: " + this.toString());

        Type bodyType = this.body.infer(env, constraintCollection);

        logger.debug(":t body `" + this.body + "` => " + bodyType);

        Type argumentType = this.argument.infer(env, constraintCollection);

        logger.debug(":t argument `" + this.argument + "` => " + argumentType);

        TypeVariable returnType = new FreshVariable();

        constraintCollection.add(bodyType, new ArrowType(argumentType, returnType));

        return returnType;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Application
                && ((Application) o).body.equals(this.body)
                && ((Application) o).argument.equals(this.argument);
    }

    @Override
    public int hashCode() {
        int result = body != null ? body.hashCode() : 0;
        result = 31 * result + (argument != null ? argument.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.body + " " + this.argument + ")";
    }
}
