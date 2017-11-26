package fr.imt.inference.type;

public class IntegerType extends TypeLiteral {

    @Override
    public boolean isTypeVariable() {
        return false;
    }

    @Override
    public boolean isArrow() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "Int";
    }
}
