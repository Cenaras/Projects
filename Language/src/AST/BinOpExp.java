package AST;

import Types.Type;
import Visitor.Visitor;

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

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Exp getLeft() {
        return left;
    }

    public Exp getRight() {
        return right;
    }
}
