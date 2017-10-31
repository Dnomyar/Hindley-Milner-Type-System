package fr.imt.inference.errors;

import fr.imt.inference.type.Type;
import fr.imt.inference.type.TypeVariable;

public class InfiniteTypeException extends Throwable {
    public InfiniteTypeException(TypeVariable typeVariable, Type type) {
    }
}
