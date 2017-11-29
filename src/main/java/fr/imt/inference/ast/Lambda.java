package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariable;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.Scheme;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import fr.imt.logger.Logger;

public class Lambda implements Expression {

    public final Variable identifier;
    public final Expression body;

    private Logger logger = Logger.instance;

    public Lambda(Variable identifier, Expression body) {
        this.identifier = identifier;
        this.body = body;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        logger.debug("Context: " + this.toString());

        TypeVariable resultType = new FreshVariable();

        Environment localEnv = env.extend(this.identifier, new Scheme(resultType));

        Type bodyType = this.body.infer(localEnv, constraintCollection);

        logger.debug(":t `" + this.body + "` => " + bodyType);

        return new ArrowType(resultType, bodyType);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Lambda &&
                ((Lambda) o).identifier.equals(this.identifier) &&
                ((Lambda) o).body.equals(this.body);
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(\\" + this.identifier + " -> " + this.body + ")";
    }
}
