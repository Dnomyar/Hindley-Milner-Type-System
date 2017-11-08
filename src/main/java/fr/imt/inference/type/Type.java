package fr.imt.inference.type;

import fr.imt.inference.Environment;
import fr.imt.inference.FreeTypeVariableContainer;
import fr.imt.inference.Substituable;
import fr.imt.inference.logger.Logger;
import io.vavr.collection.Set;


/**
 * A type is either :
 * - type literal
 * - arrow
 * - type variable
 */
public interface Type extends Substituable<Type>, FreeTypeVariableContainer, Generalizable {
    Logger logger = new Logger();

    boolean isTypeVariable();

    boolean isArrow();

    Boolean containsTheFreeVariable(TypeVariable freeTypeVariable);

    default Scheme generalize(Environment environment) {
        Set<TypeVariable> typeFTV = this.getFreeTypeVariables();
        Set<TypeVariable> envFTV = environment.getFreeTypeVariables();
        logger.debug("Generalizing... | typeFTV => " + typeFTV + " // envFTV => " + envFTV);
        Set<TypeVariable> genericTypeVariables = typeFTV.diff(envFTV);
        return new Scheme(genericTypeVariables, this);
    }
}
