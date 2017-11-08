package fr.imt.inference;

import fr.imt.inference.type.Type;

public class Constraint implements Substituable<Constraint> {

    public final Type left;
    public final Type right;

    public Constraint(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Constraint applySubstitution(SubstitutionCollection substitutions) {
        Type newLeft = left.applySubstitution(substitutions);
        Type newRight = right.applySubstitution(substitutions);

        return new Constraint(newLeft, newRight);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Constraint
                && ((Constraint) o).left.equals(this.left)
                && ((Constraint) o).right.equals(this.right);
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}
