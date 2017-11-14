package fr.imt.inference.type;

public class IntegerType extends TypeLiteral {
    @Override
    public String toString() {
        return "Int";
    }

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public boolean isArrow() {
        return false;
    }
}
