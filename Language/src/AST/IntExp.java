package AST;

import Visitor.Visitor;

public class IntExp implements Exp {

    int value;

    public IntExp(int value) {
        this.value = value;
    }

    public int eval() {
        return value;
    }

    public String toString() {
        return "IntExp(" + value + ")";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
