package fr.imt.inference;

import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;

public class ConstraintRepository {

    private Logger logger = new Logger();


    private static ConstraintRepository ourInstance = new ConstraintRepository();

    public static ConstraintRepository getInstance() {
        return ourInstance;
    }

    private ConstraintRepository() {
    }


    public void uni(Type t1, Type t2) {
        logger.debug("Adding constraint : (" + t1 + ", " + t2 + ")");
    }
}
