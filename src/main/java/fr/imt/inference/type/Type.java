package fr.imt.inference.type;


import fr.imt.inference.SubstitutionCollection;

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

    Type applySubstitution(SubstitutionCollection substitutions);
}
