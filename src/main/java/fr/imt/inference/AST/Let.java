package fr.imt.inference.AST;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.Generalizer;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;

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

        Type generalizedType = new Generalizer().generalize(env, definitionType);

        env.extend(this.identifier, generalizedType);

        Type bodyType = this.body.infer(env, constraintCollection);

        logger.debug(":t body `" + this.body + "` => " + bodyType);

        env.remove(this.identifier);

        return bodyType;
    }


    @Override
    public String toString() {
        return "let " + this.identifier + " = " + this.definition + " in " + this.body;
    }
}
