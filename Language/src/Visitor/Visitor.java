package Visitor;

import AST.*;
import Types.Type;

public interface Visitor {
    public void visit(Program n);
    public void visit(BinOpExp exp);
    public void visit(IntExp exp);
    public void visit(LetStm let);
    void visit(IdentifierExp identifierExp);
}
