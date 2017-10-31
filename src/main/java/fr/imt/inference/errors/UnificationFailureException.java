package fr.imt.inference.errors;

import fr.imt.inference.Constraint;
import fr.imt.inference.type.Type;

public class UnificationFailureException extends Throwable {
    public UnificationFailureException(Constraint constraint) {
        super("UnificationFailureException : Cannot unify type `" + constraint.left + "` with type `" + constraint.right + "`");
    }
}
