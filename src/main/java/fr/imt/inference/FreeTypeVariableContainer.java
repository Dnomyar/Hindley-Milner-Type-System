package fr.imt.inference;

import fr.imt.inference.type.TypeVariable;
import io.vavr.collection.Set;


public interface FreeTypeVariableContainer {

    Set<TypeVariable> getFreeTypeVariables();

}
