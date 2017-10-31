package fr.imt.inference;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class Constraint implements SubstitutionApplier<Constraint> {

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
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}
