package fr.imt.inference.type;


import fr.imt.inference.Substitution;

import java.util.Set;

/**
 * A type is either :
 * - type literal
 * - arrow
 * - type variable
 */
public interface Type {
    boolean isTypeVariable();
    Set<TypeVariable> getFreeTypeVariables();

    boolean isArrow();

    Type applySubstitution(Substitution substitutions);
}
