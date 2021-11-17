package AST;

import Visitor.Visitor;

public class IdentifierExp implements Exp{
    String id;
    public IdentifierExp(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String toString()
    {
        return "IdentifierExp(" + id + ")";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
