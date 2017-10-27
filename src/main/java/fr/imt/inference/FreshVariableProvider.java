package fr.imt.inference;

import fr.imt.inference.type.TypeVariable;

public class FreshVariableProvider {
    private static FreshVariableProvider ourInstance = new FreshVariableProvider();

    public static FreshVariableProvider getInstance() {
        return ourInstance;
    }

    private FreshVariableProvider() {
    }

    private String variableBaseName = "t";
    private int counter = 0;

    private int getAndIncrement(){
        return counter++;
    }

    public TypeVariable provideFresh(){
        return new TypeVariable(variableBaseName + this.getAndIncrement());
    }
}
