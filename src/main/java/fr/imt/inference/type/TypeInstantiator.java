package fr.imt.inference.type;

import fr.imt.inference.Environment;

public interface TypeInstantiator {
    /**
     * Converting a S type into a T type
     * by creating fresh names for each type variable
     * that does not appear in the current typing environment.
     *
     * @param environment the typing environment
     * @return the T type
     */
    Type instantiate(Environment environment);
}
