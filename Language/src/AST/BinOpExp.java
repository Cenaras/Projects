package AST;

import lexing.TokenType;
import Visitor.Visitor;

public class BinOpExp implements Exp {

    Exp left;
    TokenType op;
    Exp right;

    //Uncertain if this allows for weird binOps - maybe tokenType has to be some BinOpTokenType?
    public BinOpExp(Exp left, TokenType op, Exp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String toString() {
        return "BinOpExp(" + left.toString() + op.toString()  + right.toString() + ")";
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
