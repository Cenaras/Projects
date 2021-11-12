package AST;

import Visitor.Visitor;

public class LetStm implements Statement {

    IdentifierExp id;
    Exp exp;
    public LetStm(IdentifierExp id, Exp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    public String toString() {
        return "LetStm(" + id.toString() + "=" + exp.toString() + ")";
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public IdentifierExp getIdentifier() {
        return id;
    }

}
