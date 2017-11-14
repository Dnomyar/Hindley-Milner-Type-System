package fr.imt.inference.type;

import fr.imt.inference.Environment;

public interface Generalizable {
    Scheme generalize(Environment environment);
}
