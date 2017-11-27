package fr.imt.parser;

import fr.imt.inference.ast.*;
import io.vavr.collection.List;
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

        return parser.bind(p -> eof.then(retn(p)))
            .parse(Input.of(str))
            .match(
                parsed -> Either.right(parsed.result),
                error -> Either.left(error.getMsg())
            );
    }

    /**
     * Alphanumeric string
     */
    private static Parser<Character, Variable> variableParser() {
        return alphaNum.bind(var -> retn(new Variable(var)));
    }

    /**
     * Int
     */
    private static Parser<Character, TInteger> integerParser() {
        return intr.bind(intL -> retn(new TInteger(intL)));
    }

    /**
     * Int | True | False
     */
    private static Parser<Character, Literal> literalParser(Parser<Character, TInteger> integer) {
        Parser<Character, TBoolean> boolTrue = string("True").then(retn(new TBoolean(true)));
        Parser<Character, TBoolean> boolFalse = string("False").then(retn(new TBoolean(false)));
        Parser<Character, TBoolean> bool = choice(boolTrue, boolFalse);

        return choice(bool, integer);
    }

    /**
     * + | - | * | /
     */
    private static List<Parser<Character, Operator>> arithmeticOperatorParsers() {
        return Operator.all().map(operator -> chr(operator.toChar()).bind(c -> retn(operator)));
    }

    /**
     * <Int> <Operator> <Int>
     */
    private static Parser<Character, BinaryArithmeticOperation> arithmeticOperationParser(Parser<Character, TInteger> integer, Parser<Character, Variable> variable) {
        return
            choice(integer, variable).bind(left ->
                space(
                    choice(IList.of(arithmeticOperatorParsers())).bind(operator ->
                        space(
                            choice(integer, variable).bind(right ->
                                retn(new BinaryArithmeticOperation(left, right, operator)))))));
    }

    /**
     * \ <var+> -> <exp | var>
     */
    private static Parser<Character, Lambda> lambdaParser(Parser<Character, Variable> variable, Parser<Character, Expression> expression) {
        return
            chr('\\').then(
                space(
                    variable.bind(id ->
                        space(retn(id))
                    ).many().bind(ids ->
                        space(
                            string("->").then(
                                space(
                                    choice(variable, expression).bind(body ->
                                        space(
                                            retn(toLambda(ids, body))))))))));
    }

    /**
     * Helper to create Lambda expression from multiple identifiers
     */
    private static Lambda toLambda(IList<Variable> ids, Expression body) {
        return (ids.size() == 1)
                ? new Lambda(ids.head(), body)
                : new Lambda(ids.head(), toLambda(ids.tail(), body));
    }

    /**
     * let <var> = <exp> in <exp>
     */
    private static Parser<Character, Let> letParser(Parser<Character, Variable> variable, Parser<Character, Expression> expression) {
        return
            string("let").then(
                space(
                    variable.bind(id ->
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
     * app <exp | var> <exp>
     */
    private static Parser<Character, Application> appParser(Parser<Character, Variable> variable, Parser<Character, Expression> expression) {
        return
            string("app").then(
                space(
                    choice(variable, expression).bind(body ->
                        space(
                            expression.bind(arg ->
                                retn(new Application(body, arg)))))));
    }

    private static Parser<Character, Expression> expressionParser() {
        Parser.Ref<Character, Expression> expression = Parser.ref();

        // Build a basic expression (i.e. without parentheses)
        Parser.Ref<Character, Expression> basicExpression = Parser.ref();
        Parser<Character, Variable> variable = variableParser();
        Parser<Character, TInteger> integer = integerParser();
        Parser<Character, Literal> literal = literalParser(integer);
        Parser<Character, BinaryArithmeticOperation> arithmeticOperation = attempt(arithmeticOperationParser(integer, variable));
        Parser<Character, Lambda> lambda = lambdaParser(variable, expression);
        Parser<Character, Let> let = letParser(variable, expression);
        Parser<Character, Application> app = appParser(variable, expression);
        basicExpression.set(choice(arithmeticOperation, literal, lambda, let, app));

        // Build a parenthesized expression (i.e. expression with parentheses)
        Parser<Character, Expression> parenthesizedExpression =
            chr('(').then(
                space(
                    basicExpression.bind(exp ->
                        space(
                            chr(')').then(
                                retn(exp))))));

        // An expression is either a parenthesized or basic expression
        expression.set(choice(basicExpression, parenthesizedExpression));

        return expression;
    }

    private static <T> Parser<Character, T> space(Parser<Character, T> parser) {
        return wspaces.then(parser);
    }

}
