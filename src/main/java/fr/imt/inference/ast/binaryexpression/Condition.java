package fr.imt.inference.ast.binaryexpression;

import fr.imt.inference.FreshVariable;
import fr.imt.inference.ast.Expression;
import fr.imt.inference.ast.binaryexpression.operators.EqualityOperator;
import fr.imt.inference.type.*;

public class Condition extends BinaryExpression {

    private Expression left;
    private Expression right;
    private EqualityOperator operator;

    public Condition(Expression left, Expression right, EqualityOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Expression left() {
        return this.left;
    }

    @Override
    public Expression right() {
        return this.right;
    }

    @Override
    protected Type constraintType() {
        TypeVariable operatorType = new FreshVariable();
        return  new ArrowType(operatorType, new ArrowType(operatorType, new BooleanType()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Condition condition = (Condition) o;

        if (left != null ? !left.equals(condition.left) : condition.left != null) return false;
        if (right != null ? !right.equals(condition.right) : condition.right != null) return false;
        return operator == condition.operator;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + this.left + " " + this.operator + " " + this.right + ")";
    }
}
