package fr.imt.inference;

public interface SubstitutionApplier<T> {

    T applySubstitution(SubstitutionCollection substitutions);

}
