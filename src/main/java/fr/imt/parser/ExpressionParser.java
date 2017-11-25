package fr.imt.parser;

import fr.imt.inference.ast.*;
import io.vavr.control.Either;
import org.javafp.parsecj.Parser;
import org.javafp.parsecj.input.Input;

import static org.javafp.parsecj.Combinators.choice;
import static org.javafp.parsecj.Combinators.retn;
import static org.javafp.parsecj.Text.*;

public class ExpressionParser implements Parsable<Expression> {

    private final Parser<Character, Expression> expressionParser = expressionParser();

    @Override
    public Either<String, Expression> parse(String str) {
        return expressionParser.parse(Input.of(str))
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
            string("\\").then(
                space(
                    variable.bind(id ->
                        space(
                            string("->").then(
                                space(
                                    choice(expression, variable).bind(exp ->
                                        space(
                                            retn(new Lambda(id, exp))))))))));
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
                            string("=").then(
                                space(
                                    expression.bind(def ->
                                        space(
                                            string("in").then(
                                                space(
                                                    expression.bind(body ->
                                                        retn(new Let(id, def, body)))))))))))));
    }

    /**
     * (<exp>) (<exp>)
     */
    private static Parser<Character, Application> appParser(Parser<Character, Expression> expression) {
        return
            chr('(').then(
                space(
                    expression.bind(body ->
                        space(
                            chr(')').then(
                                space(
                                    chr('(').then(
                                        space(
                                            expression.bind(arg ->
                                                space(
                                                    chr(')').then(
                                                        retn(new Application(body, arg)))))))))))));

    }

    private static <T> Parser<Character, T> space(Parser<Character, T> parser) {
        return wspaces.then(parser);
    }

    private static Parser<Character, Expression> expressionParser() {
        Parser.Ref<Character, Expression> expression = Parser.ref();

        Parser<Character, Variable> variable = variableParser();
        Parser<Character, Literal> literal = literalParser();
        Parser<Character, Lambda> lambda = lambdaParser(variable, expression);
        Parser<Character, Let> let = letParser(variable, expression);
        Parser<Character, Application> app = appParser(expression);

        return expression.set(choice(literal, lambda, let, app));
    }
}
