package fr.imt.parser;

import fr.imt.inference.ast.*;
import io.vavr.control.Either;
import org.javafp.data.Unit;
import org.javafp.parsecj.Parser;
import org.javafp.parsecj.input.Input;

import static org.javafp.parsecj.Combinators.choice;
import static org.javafp.parsecj.Combinators.eof;
import static org.javafp.parsecj.Combinators.retn;
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

    private static Parser<Character, Variable> variableParser() {
        return alphaNum.bind(var -> retn(new Variable(var)));
    }

    /**
     * Int | True | False
     */
    private static Parser<Character, Literal> literalParser() {
        Parser<Character, TInteger> intLit = intr.bind(intL -> retn(new TInteger(intL)));
        Parser<Character, TBoolean> boolTrueLit = string("True").then(retn(new TBoolean(true)));
        Parser<Character, TBoolean> boolFalseLit = string("False").then(retn(new TBoolean(false)));
        Parser<Character, TBoolean> boolLit = choice(boolTrueLit, boolFalseLit);

        return choice(boolLit, intLit);
    }

    /**
     * \ <var> -> <exp | var>
     */
    private static Parser<Character, Lambda> lambdaParser(Parser<Character, Variable> variable, Parser<Character, Expression> expression) {
        return
            chr('\\').then(
                space(
                    variable.bind(id ->
                        space(
                            string("->").then(
                                space(
                                    choice(expression, variable).bind(body ->
                                        space(
                                            retn(new Lambda(id, body))))))))));
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
                    choice(expression, variable).bind(body ->
                        space(
                            expression.bind(arg ->
                                retn(new Application(body, arg)))))));
    }

    private static Parser<Character, Expression> expressionParser() {
        Parser.Ref<Character, Expression> expression = Parser.ref();

        // Build a basic expression (i.e. without parentheses)
        Parser.Ref<Character, Expression> basicExpression = Parser.ref();
        Parser<Character, Variable> variable = variableParser();
        Parser<Character, Literal> literal = literalParser();
        Parser<Character, Lambda> lambda = lambdaParser(variable, expression);
        Parser<Character, Let> let = letParser(variable, expression);
        Parser<Character, Application> app = appParser(variable, expression);
        basicExpression.set(choice(literal, lambda, let, app));

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
