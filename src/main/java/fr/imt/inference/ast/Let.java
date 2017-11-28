package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.Scheme;
import fr.imt.inference.type.Type;
import fr.imt.logger.Logger;

public class Let implements Expression {
    public final Variable identifier;
    public final Expression definition;
    public final Expression body;

    private final Logger logger = new Logger();

    public Let(Variable identifier, Expression definition, Expression body) {
        this.identifier = identifier;
        this.definition = definition;
        this.body = body;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        logger.debug("Context: " + this.toString());

        Type definitionType = this.definition.infer(env, constraintCollection);

        logger.debug(":t definition `" + this.definition + "` => " + definitionType);

        Scheme generalizedType = definitionType.generalize(env);

        final Environment localEnv = env.extend(this.identifier, generalizedType);

        Type bodyType = this.body.infer(localEnv, constraintCollection);

        logger.debug(":t body `" + this.body + "` => " + bodyType);

        return bodyType;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Let
                && ((Let) o).identifier.equals(this.identifier)
                && ((Let) o).definition.equals(this.definition)
                && ((Let) o).body.equals(this.body);
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "let " + this.identifier + " = " + this.definition + " in " + this.body;
    }
}
