package fr.imt.inference.errors;

public class NonexistentVariableException extends RuntimeException {
    public <T> NonexistentVariableException(T variable) {
        super("Variable `" + variable + "` not found");
    }
}
