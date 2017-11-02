package fr.imt.inference.type;

import fr.imt.inference.Substituable;

import java.util.Set;

/**
 * A type is either :
 * - type literal
 * - arrow
 * - type variable
 */
public interface Type extends Substituable<Type> {
    boolean isTypeVariable();

    boolean isArrow();

    Set<TypeVariable> getFreeTypeVariables();

    Boolean containsTheFreeVariable(TypeVariable freeTypeVariable);
}
