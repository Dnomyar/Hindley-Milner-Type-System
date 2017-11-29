package fr.imt.inference.ast.factory;

import fr.imt.inference.ast.*;
import fr.imt.inference.ast.binaryexpression.Condition;
import fr.imt.inference.ast.binaryexpression.operators.ArithmeticOperator;
import fr.imt.inference.ast.binaryexpression.BinaryArithmeticOperation;
import fr.imt.inference.ast.binaryexpression.operators.EqualityOperator;

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

    public static BinaryArithmeticOperation Ope(Expression left, Expression right, ArithmeticOperator operator) {
        return new BinaryArithmeticOperation(left, right, operator);
    }

    public static Condition Con(Expression left, Expression right, EqualityOperator operator) {
        return new Condition(left, right, operator);
    }


    public static If If(Condition condition, Expression thenExpression, Expression elseExpression) {
        return new If(condition, thenExpression, elseExpression);
    }

    public static TBoolean Bool(Boolean value) {
        return new TBoolean(value);
    }

    public static TInteger Int(Integer value) {
        return new TInteger(value);
    }
}
