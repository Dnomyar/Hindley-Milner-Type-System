package fr.imt.inference;

import fr.imt.inference.ast.Expression;
import fr.imt.inference.ast.Variable;
import fr.imt.inference.errors.NonexistentVariableException;
import fr.imt.inference.type.Scheme;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;
import fr.imt.logger.Logger;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

public class Environment implements FreeTypeVariableContainer {

    private Logger logger = new Logger();
    private Map<Expression, Scheme> env;

    public Environment() {
        this.env = HashMap.empty();
    }

    public void extend(Expression expression, Scheme scheme) {
        logger.trace("Extend env: " + expression + " -> " + scheme);
        this.env = this.env.put(expression, scheme);
    }

    public void remove(Expression expression) {
        logger.trace("Remove : " + expression + " linked to " + this.env.get(expression).get() + " from env.");
        this.env = this.env.remove(expression);
    }

    public Type get(Variable variable) {
        if (this.env.containsKey(variable)) {
            return this.env.get(variable).get().instantiate(this);
        }

        throw new NonexistentVariableException(variable);
    }

    @Override
    public Set<TypeVariable> getFreeTypeVariables() {
        return env.values().toSet().flatMap(Scheme::getFreeTypeVariables);
    }
}
