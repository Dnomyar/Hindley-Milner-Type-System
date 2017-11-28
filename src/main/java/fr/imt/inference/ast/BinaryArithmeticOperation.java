package fr.imt.inference.ast;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.type.IntegerType;
import fr.imt.inference.type.Type;

public class BinaryArithmeticOperation implements Expression {

    public final Expression left;
    public final Expression right;
    public final Operator operator;

    public BinaryArithmeticOperation(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        final Type leftType = left.infer(env, constraintCollection);
        final Type rightType = right.infer(env, constraintCollection);

        constraintCollection.add(leftType, new IntegerType());
        constraintCollection.add(rightType, new IntegerType());

        return new IntegerType();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BinaryArithmeticOperation
            && ((BinaryArithmeticOperation) o).right.equals(this.right)
            && ((BinaryArithmeticOperation) o).left.equals(this.left)
            && ((BinaryArithmeticOperation) o).operator.equals(this.operator);
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
        return "(" + left.toString() + ' ' + operator.toString() + ' ' + right.toString() + ')';
    }
}
