package fr.imt.inference.ast.binaryexpression;

import fr.imt.inference.ConstraintCollection;
import fr.imt.inference.Environment;
import fr.imt.inference.FreshVariable;
import fr.imt.inference.ast.Expression;
import fr.imt.inference.type.ArrowType;
import fr.imt.inference.type.IntegerType;
import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public abstract class BinaryExpression implements Expression {

    abstract public Expression left();
    abstract public Expression right();
    abstract protected Type returnType();

    @Override
    public Type infer(Environment env, ConstraintCollection constraintCollection) {
        final Type leftType = left().infer(env, constraintCollection);
        final Type rightType = right().infer(env, constraintCollection);

        TypeVariable returnType = new FreshVariable();

        ArrowType type1 = new ArrowType(leftType, new ArrowType(rightType, returnType));
        ArrowType type2 = new ArrowType(new IntegerType(), new ArrowType(new IntegerType(), returnType()));

        constraintCollection.add(type1, type2);

        return returnType;
    }

}
