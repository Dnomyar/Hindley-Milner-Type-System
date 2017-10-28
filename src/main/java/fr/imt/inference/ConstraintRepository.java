package fr.imt.inference;

import com.google.inject.Singleton;
import fr.imt.inference.logger.Logger;
import fr.imt.inference.type.Type;

@Singleton
public class ConstraintRepository {

    private final Logger logger = new Logger(getClass());

    public void uni(Type t1, Type t2) {
        logger.debug("Adding constraint : (" + t1 + ", " + t2 + ")");
    }
}
