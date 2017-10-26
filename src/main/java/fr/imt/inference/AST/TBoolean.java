package fr.imt.inference.AST;

public class TBoolean implements Literal {
    public final Boolean value;

    public TBoolean(Boolean value) {
        this.value = value;
    }
}
