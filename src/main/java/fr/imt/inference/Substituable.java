package fr.imt.inference;

public interface Substituable<T> {

    T applySubstitution(SubstitutionCollection substitutions);

}
