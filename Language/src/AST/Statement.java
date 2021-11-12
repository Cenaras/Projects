package AST;

import Visitor.Visitor;

public interface Statement {
    public void accept(Visitor v);

}
