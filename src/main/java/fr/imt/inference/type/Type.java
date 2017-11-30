package fr.imt.inference.type;

import fr.imt.inference.Environment;
import fr.imt.inference.FreeTypeVariableContainer;
import fr.imt.inference.Substituable;

public interface Type extends Substituable<Type>, FreeTypeVariableContainer {
    boolean isTypeVariable();

    boolean isArrow();

    Boolean containsTheFreeVariable(TypeVariable freeTypeVariable);

    /**
     * Convert a type T into a type S by closing overall free type variables in a type scheme.
     *
     * @param environment all the variables
     * @return a Schema containing all the free variables
     */
    Scheme generalize(Environment environment);
}
