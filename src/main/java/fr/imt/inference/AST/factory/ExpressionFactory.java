package fr.imt.inference.AST.factory;

import fr.imt.inference.AST.*;

public class ExpressionFactory {
    public static Lambda Lamb(Variable variable, Expression expression) {
        return new Lambda(variable, expression);
    }

    public static Lambda Lamb(Variable arg1, Variable arg2, Expression expression) {
        return Lamb(arg1, Lamb(arg2, expression));
    }

    public static Variable Var(String name) {
        return new Variable(name);
    }

    public static Application App(Expression body, Expression argument) {
        return new Application(body, argument);
    }

    public static Application App(Expression body, Expression arg1, Expression arg2) {
        return App(App(body, arg1), arg2);
    }

    public static Let Let(Variable identifier, Expression definition, Expression body) {
        return new Let(identifier, definition, body);
    }

    public static TBoolean Bool(Boolean value) {
        return new TBoolean(value);
    }

    public static TInteger Int(Integer value) {
        return new TInteger(value);
    }
}
