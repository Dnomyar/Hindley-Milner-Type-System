package fr.imt.inference.AST.factory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.imt.inference.AST.*;

@Singleton
public class ExpressionFactory {


    @Inject
    public ExpressionFactory(
    ) {
    }

    public Lambda Lamb(Variable variable, Expression expression) {
        return new Lambda(variable, expression);
    }

    public Lambda Lamb(Variable arg1, Variable arg2, Expression expression) {
        return Lamb(arg1, Lamb(arg2, expression));
    }

    public Variable Var(String name) {
        return new Variable(name);
    }

    public Application App(Expression body, Expression argument) {
        return new Application(body, argument);
    }

    public Application App(Expression body, Expression arg1, Expression arg2) {
        return App(App(body, arg1), arg2);
    }

    public Let Let(Variable identifier, Expression definition, Expression body) {
        return new Let(identifier, definition, body);
    }

    public TBoolean Bool(Boolean value) {
        return new TBoolean(value);
    }

    public TInteger Int(Integer value) {
        return new TInteger(value);
    }
}
