package fr.imt.inference.type;


import fr.imt.inference.SubstitutionApplier;

import java.util.Set;

/**
 * A type is either :
 * - type literal
 * - arrow
 * - type variable
 */
public interface Type extends SubstitutionApplier<Type> {
    boolean isTypeVariable();

    boolean isArrow();

    Set<TypeVariable> getFreeTypeVariables();
}
