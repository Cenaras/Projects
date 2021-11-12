package AST;

import Visitor.Visitor;

public class VarExp implements Exp {

    String id;
    public VarExp(String id)
    {
        this.id = id;
    }


    @Override
    public String toString() {
        return null;
    }

    @Override
    public void accept(Visitor v) {

    }
}
