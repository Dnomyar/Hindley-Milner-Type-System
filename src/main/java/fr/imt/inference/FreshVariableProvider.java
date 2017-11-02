package fr.imt.inference;

import com.google.inject.Singleton;
import fr.imt.inference.type.TypeVariable;

@Singleton
public class FreshVariableProvider {
    private static FreshVariableProvider ourInstance = new FreshVariableProvider();
    private String variableBaseName = "t";
    private int counter = 0;

    private FreshVariableProvider() {
    }

    public static FreshVariableProvider getInstance() {
        return ourInstance;
    }

    private int getAndIncrement() {
        return counter++;
    }

    public TypeVariable provideFresh() {
        return new TypeVariable(variableBaseName + this.getAndIncrement());
    }
}
