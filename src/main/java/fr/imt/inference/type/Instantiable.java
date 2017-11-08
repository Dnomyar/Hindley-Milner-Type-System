package fr.imt.inference.type;

import fr.imt.inference.Environment;

public interface Instantiable {
    Type instantiate(Environment environment);
}
