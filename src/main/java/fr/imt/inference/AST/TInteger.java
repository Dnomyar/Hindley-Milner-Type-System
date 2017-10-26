package fr.imt.inference.AST;

public class TInteger implements Literal {
    public final Integer value;

    public TInteger(Integer value) {
        this.value = value;
    }
}
