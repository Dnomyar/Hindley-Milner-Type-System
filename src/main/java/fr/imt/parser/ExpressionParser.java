package fr.imt.parser;

import fr.imt.inference.ast.*;
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
    private Parser<Character, Operator> arithmeticOperatorParser() {
        return choice(IList.of(Operator.all().map(operator -> chr(operator.toChar()).bind(c -> retn(operator)))));
    }

    /**
     * op <exp> <Operator> <exp>
     */
    private Parser<Character, BinaryArithmeticOperation> arithmeticOperationParser(Parser<Character, Expression> expression) {
        return
            string("op").then(
                space(
                    expression.bind(left ->
                        space(
                            arithmeticOperatorParser().bind(operator ->
                                space(
                                    expression.bind(right ->
                                        retn(new BinaryArithmeticOperation(left, right, operator)))))))));
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

    private Parser<Character, Expression> expressionParser() {
        Parser.Ref<Character, Expression> expression = Parser.ref();

        // Build a basic expression (i.e. without parentheses)
        Parser.Ref<Character, Expression> basicExpression = Parser.ref();
        Parser<Character, Variable> variable = variableParser();
        Parser<Character, Literal> literal = literalParser();
        Parser<Character, BinaryArithmeticOperation> arithmeticOperation = attempt(arithmeticOperationParser(expression));
        Parser<Character, Lambda> lambda = lambdaParser(expression);
        Parser<Character, Let> let = attempt(letParser(expression));
        Parser<Character, Application> app = attempt(appParser(expression));
        basicExpression.set(choice(arithmeticOperation, literal, lambda, let, app, variable));

        // Build a addParentheses expression (i.e. expression with parentheses)
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
