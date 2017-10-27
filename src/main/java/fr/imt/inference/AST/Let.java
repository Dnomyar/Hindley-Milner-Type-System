package fr.imt.inference.AST;

import fr.imt.inference.Environment;
import fr.imt.inference.Generalizer;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;

public class Let implements Expression {


    private Logger logger = new Logger(getClass());

    public final Variable identifier;
    public final Expression definition;
    public final Expression body;

    public Let(Variable identifier, Expression definition, Expression body) {
        this.identifier = identifier;
        this.definition = definition;
        this.body = body;
    }

    @Override
    public Type infer(Environment env) {

        logger.debug("Current exp " + this.toString());


        Type definitionType = this.definition.infer(env);

        logger.debug("Type for definition `" + this.definition + "` is " + definitionType);


        Type generalizedType = new Generalizer().generalize(env, definitionType);

        env.extend(this.identifier, generalizedType);

        Type bodyType = this.body.infer(env);

        logger.debug("Type for bodyType `" + this.body + "` is " + bodyType);


        env.remove(this.identifier);

        return bodyType;
    }


    @Override
    public String toString() {
        return "let " + this.identifier + " = " + this.definition + " in " + this.body;
    }
}
