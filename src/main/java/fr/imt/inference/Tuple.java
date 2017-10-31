package fr.imt.inference;

public class Tuple<TLEFT, TRIGHT> {
    public final TLEFT left;
    public final TRIGHT right;

    public Tuple(TLEFT left, TRIGHT right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String toString() {
        return "(" + this.left + ", " + this.right + ")";
    }
}
