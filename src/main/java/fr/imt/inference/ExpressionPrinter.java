package fr.imt.inference;

import fr.imt.inference.AST.*;

import java.text.MessageFormat;

public class ExpressionPrinter {
    public String visit(Expression expression) {
        if (expression instanceof TBoolean) {
            return this.visit((TBoolean) expression);
        }

        if (expression instanceof TInteger) {
            return this.visit((TInteger) expression);
        }

        if (expression instanceof Variable) {
            return this.visit((Variable) expression);
        }

        if (expression instanceof Lambda) {
            return this.visit((Lambda) expression);
        }

        if (expression instanceof Application) {
            return this.visit((Application) expression);
        }

        if (expression instanceof Let) {
            return this.visit((Let) expression);
        }

        return "";
    }

    public String visit(Lambda lambda) {
        return MessageFormat.format(
                "(\\{0} -> {1})",
                this.visit(lambda.identifier),
                this.visit(lambda.body)
        );
    }

    public String visit(Application application) {
        return MessageFormat.format("({0} {1})", this.visit(application.body), this.visit(application.argument));
    }

    public String visit(Let let) {
        return MessageFormat.format(
                "let {0} = {1} in {2}",
                this.visit(let.identifier),
                this.visit(let.definition),
                this.visit(let.body)
        );
    }

    public String visit(Variable variable) {
        return variable.name;
    }

    public String visit(TBoolean bool) {
        return bool.value ? "True" : "False";
    }

    public String visit(TInteger integer) {
        return integer.value.toString();
    }
}
