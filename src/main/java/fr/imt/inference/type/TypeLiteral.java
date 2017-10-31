package fr.imt.inference.type;

import fr.imt.inference.SubstitutionCollection;

public abstract class TypeLiteral implements Type {

    @Override
    public Type applySubstitution(SubstitutionCollection substitutions) {
        return this;
    }
}
