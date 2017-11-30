package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.ast.binaryexpression.Condition;
import fr.imt.inference.type.BooleanType;
import fr.imt.inference.type.Type;

public class If implements Expression {

    private Condition condition;
    private Expression thenExpression;
    private Expression elseExpression;

    public If(Condition condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {

        Type conditionInferred = condition.infer(env, constraintCollection);

        Type thenExpressionInferred = thenExpression.infer(env, constraintCollection);

        Type elseExpressionInferred = elseExpression.infer(env, constraintCollection);

        constraintCollection.add(conditionInferred, new BooleanType());
        constraintCollection.add(thenExpressionInferred, elseExpressionInferred);

        // does not matter which type among (thenExpressionInferred, elseExpressionInferred) because a constraint is added
        return thenExpressionInferred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        If anIf = (If) o;

        if (condition != null ? !condition.equals(anIf.condition) : anIf.condition != null) return false;
        if (thenExpression != null ? !thenExpression.equals(anIf.thenExpression) : anIf.thenExpression != null)
            return false;
        return elseExpression != null ? elseExpression.equals(anIf.elseExpression) : anIf.elseExpression == null;
    }

    @Override
    public int hashCode() {
        int result = condition != null ? condition.hashCode() : 0;
        result = 31 * result + (thenExpression != null ? thenExpression.hashCode() : 0);
        result = 31 * result + (elseExpression != null ? elseExpression.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(if " + condition + " then " + thenExpression + " else " + elseExpression + ")";
    }
}
