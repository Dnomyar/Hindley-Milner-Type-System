package fr.imt.inference.type;

public class ArrowType implements Type {
    private final Type left;
    private final Type right;

    public ArrowType(Type left, Type right) {

        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.left + " -> " + this.right;
    }
}
