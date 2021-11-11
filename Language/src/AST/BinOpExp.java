package AST;

public class BinOpExp implements Exp {

    Exp left;
    String op;
    Exp right;

    public BinOpExp(Exp left, String op, Exp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String toString() {
        return "BinOpExp(" + left.toString() + op  + right.toString() + ")";
    }

}
