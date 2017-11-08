package fr.imt.inference.type;

public class BooleanType extends TypeLiteral {
    @Override
    public String toString() {
        return "Bool";
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
