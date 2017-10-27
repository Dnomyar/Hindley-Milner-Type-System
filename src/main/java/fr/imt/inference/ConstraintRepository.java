package fr.imt.inference;

import fr.imt.inference.type.Type;

public class ConstraintRepository {
    private static ConstraintRepository ourInstance = new ConstraintRepository();

    public static ConstraintRepository getInstance() {
        return ourInstance;
    }

    private ConstraintRepository() {
    }


    public void uni(Type t1, Type t2) {

    }
}
