package fr.imt.inference.ast.binaryexpression;

import fr.imt.inference.ast.Expression;
import fr.imt.inference.ast.binaryexpression.operators.ArithmeticOperator;
import fr.imt.inference.type.IntegerType;
import fr.imt.inference.type.Type;

public class ArithmeticOperation extends BinaryExpression {

    private final Expression left;
    private final Expression right;
    public final ArithmeticOperator arithmeticOperator;

    public ArithmeticOperation(Expression left, Expression right, ArithmeticOperator operator) {
        this.left = left;
        this.right = right;
        this.arithmeticOperator = operator;
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
    protected Type returnType() {
        return new IntegerType();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArithmeticOperation
                && ((ArithmeticOperation) o).right.equals(this.right)
                && ((ArithmeticOperation) o).left.equals(this.left)
                && ((ArithmeticOperation) o).arithmeticOperator.equals(this.arithmeticOperator);
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (arithmeticOperator != null ? arithmeticOperator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + ' ' + arithmeticOperator.toString() + ' ' + right.toString() + ')';
    }
}
