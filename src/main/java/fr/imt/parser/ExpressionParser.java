package fr.imt.parser;

import fr.imt.inference.ast.*;
import fr.imt.inference.ast.binaryexpression.ArithmeticOperation;
import fr.imt.inference.ast.binaryexpression.BinaryExpression;
import fr.imt.inference.ast.binaryexpression.Condition;
import fr.imt.inference.ast.binaryexpression.operators.ArithmeticOperator;
import fr.imt.inference.ast.binaryexpression.operators.EqualityOperator;
import io.vavr.control.Either;
import org.javafp.data.IList;
import org.javafp.data.Unit;
import org.javafp.parsecj.Parser;
import org.javafp.parsecj.input.Input;

import static org.javafp.parsecj.Combinators.*;
import static org.javafp.parsecj.Text.*;

public class ExpressionParser implements Parsable<Expression> {

    private final Parser<Character, Expression> parser = expressionParser();

    @Override
    public Either<String, Expression> parse(String str) {
        Parser<Character, Unit> eof = eof();

        try {
            return parser.bind(p -> eof.then(retn(p)))
                .parse(Input.of(str))
                .match(
                    parsed -> Either.right(parsed.result),
                    error -> Either.left(error.getMsg())
                );
        } catch (Throwable e) {
            return Either.left("Cannot parse input");
        }
    }

    /**
     * Alphanumeric string
     */
    private Parser<Character, Variable> variableParser() {
        return alphaNum.bind(var -> retn(new Variable(var)));
    }

    /**
     * Int
     */
    private Parser<Character, TInteger> integerParser() {
        return intr.bind(intL -> retn(new TInteger(intL)));
    }

    /**
     * True | False
     */
    private Parser<Character, TBoolean> boolParser() {
        Parser<Character, TBoolean> boolTrue = string("True").then(retn(new TBoolean(true)));
        Parser<Character, TBoolean> boolFalse = string("False").then(retn(new TBoolean(false)));
        return choice(boolTrue, boolFalse);
    }

    /**
     * Int | True | False
     */
    private Parser<Character, Literal> literalParser() {
        return choice(boolParser(), integerParser());
    }

    /**
     * + | - | * | /
     */
    private Parser<Character, ArithmeticOperator> arithmeticOperatorParser() {
        return choice(IList.of(ArithmeticOperator.all()
                    .map(operator -> chr(operator.toChar())
                        .bind(c -> retn(operator)))));
    }

    /**
     * ==
     */
    private Parser<Character, EqualityOperator> equalityOperatorParser() {
        return choice(IList.of(EqualityOperator.all()
                    .map(operator -> string(operator.toString())
                        .bind(c -> retn(operator)))));
    }

    /**
     * op <exp> <ArithmeticOperator> <exp>
     */
    private Parser<Character, ArithmeticOperation> arithmeticOperationParser(Parser<Character, Expression> expression) {
        return
            string("op").then(
                space(
                    expression.bind(left ->
                        space(
                            arithmeticOperatorParser().bind(operator ->
                                space(
                                    expression.bind(right ->
                                        retn(new ArithmeticOperation(left, right, operator)))))))));
    }

    /**
     * con <exp> <EqualityOperator> <exp>
     */
    private Parser<Character, Condition> conditionParser(Parser<Character, Expression> expression) {
        return
            string("con").then(
                space(
                    expression.bind(left ->
                        space(
                            equalityOperatorParser().bind(operator ->
                                space(
                                    expression.bind(right ->
                                        retn(new Condition(left, right, operator)))))))));
    }

    /**
     * \ <var+> -> <exp>
     */
    private Parser<Character, Lambda> lambdaParser(Parser<Character, Expression> expression) {
        return
            chr('\\').then(
                space(
                    variableParser().bind(id ->
                        space(retn(id))
                    ).many().bind(ids ->
                        space(
                            string("->").then(
                                space(
                                    expression.bind(body ->
                                        space(
                                            retn(toLambda(ids, body))))))))));
    }

    /**
     * Helper to create Lambda expression from multiple identifiers
     */
    private Lambda toLambda(IList<Variable> ids, Expression body) {
        return (ids.size() == 1)
                ? new Lambda(ids.head(), body)
                : new Lambda(ids.head(), toLambda(ids.tail(), body));
    }

    /**
     * let <var> = <exp> in <exp>
     */
    private Parser<Character, Let> letParser(Parser<Character, Expression> expression) {
        return
            string("let").then(
                space(
                    variableParser().bind(id ->
                        space(
                            chr('=').then(
                                space(
                                    expression.bind(def ->
                                        space(
                                            string("in").then(
                                                space(
                                                    expression.bind(body ->
                                                        retn(new Let(id, def, body)))))))))))));
    }

    /**
     * app <exp> <exp>
     */
    private Parser<Character, Application> appParser(Parser<Character, Expression> expression) {
        return
            string("app").then(
                space(
                    expression.bind(body ->
                        space(
                            expression.bind(arg ->
                                retn(new Application(body, arg)))))));
    }

    /**
     * if <exp> <exp>
     */
    private Parser<Character, If> ifParser(Parser<Character, Expression> expression) {
        return
            string("if").then(
                space(conditionParser(expression).bind(condition ->
                    space(string("then").then(space(
                        expression.bind(thenExpr ->
                            space(string("else").then(space(
                                expression.bind(elseExpr ->
                                    retn(new If(condition, thenExpr, elseExpr)))))))))))));
    }

    private Parser<Character, Expression> expressionParser() {
        Parser.Ref<Character, Expression> expression = Parser.ref();

        // Build a basic expression (i.e. without parentheses)
        Parser.Ref<Character, Expression> basicExpression = Parser.ref();
        Parser<Character, Variable> variable = variableParser();
        Parser<Character, Literal> literal = literalParser();
        Parser<Character, Lambda> lambda = lambdaParser(expression);
        Parser<Character, Let> let = attempt(letParser(expression));
        Parser<Character, Application> app = attempt(appParser(expression));
        Parser<Character, If> ifExp = attempt(ifParser(expression));

        Parser<Character, ArithmeticOperation> arithmeticOperation = attempt(arithmeticOperationParser(expression));
        Parser<Character, Condition> condition = attempt(conditionParser(expression));
        Parser<Character, BinaryExpression> binaryExpression = choice(arithmeticOperation, condition);

        basicExpression.set(choice(binaryExpression, ifExp, literal, lambda, let, app, variable));

        // Build a parenthesized expression (i.e. expression with parentheses)
        Parser<Character, Expression> parenthesizedExpression = addParentheses(basicExpression);

        // An expression is either a parenthesized or basic expression
        expression.set(choice(basicExpression, parenthesizedExpression));

        return expression;
    }

    private <T> Parser<Character, T> addParentheses(Parser<Character, T> expression) {
        return
            chr('(').then(
                space(
                    expression.bind(exp ->
                        space(
                            chr(')').then(
                                retn(exp))))));
    }

    private <T> Parser<Character, T> space(Parser<Character, T> parser) {
        return wspaces.then(parser);
    }

}
