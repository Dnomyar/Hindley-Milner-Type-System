package fr.imt.inference;

import fr.imt.inference.AST.Expression;
import fr.imt.inference.AST.Variable;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;


public class Environment {

    private Logger logger = new Logger();
    private Map<Expression, Type> env;

    public Environment() {
        this.env = HashMap.empty();
    }

    public void extend(Expression expression, Type type) {
        logger.trace("Extend env: " + expression + " -> " + type);
        this.env = this.env.put(expression, type);
    }

    public void remove(Expression expression) {
        logger.trace("Remove : " + expression + " linked to " + this.env.get(expression).get() + " from env.");
        this.env = this.env.remove(expression);
    }

    public Type get(Variable variable) {
        return this.env.get(variable).get();
    }
}
