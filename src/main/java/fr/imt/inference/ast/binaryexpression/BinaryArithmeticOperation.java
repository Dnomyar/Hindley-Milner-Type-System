package fr.imt.inference.ast.binaryexpression;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariable;
import fr.imt.inference.ast.Expression;
import fr.imt.inference.ast.binaryexpression.operators.ArithmeticOperator;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.IntegerType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class BinaryArithmeticOperation extends BinaryExpression {

    private final Expression left;
    private final Expression right;
    public final ArithmeticOperator arithmeticOperator;

    public BinaryArithmeticOperation(Expression left, Expression right, ArithmeticOperator operator) {
        this.left = left;
        this.right = right;
        this.arithmeticOperator = operator;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BinaryArithmeticOperation
            && ((BinaryArithmeticOperation) o).right.equals(this.right)
            && ((BinaryArithmeticOperation) o).left.equals(this.left)
            && ((BinaryArithmeticOperation) o).arithmeticOperator.equals(this.arithmeticOperator);
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
}
