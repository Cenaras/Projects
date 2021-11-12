package Visitor;

import AST.BinOpExp;
import AST.IntExp;
import AST.LetStm;
import AST.Program;
import Types.Type;

public interface Visitor {
    public void visit(Program n);
    public void visit(BinOpExp exp);
    public void visit(IntExp exp);
    public void visit(LetStm let);
}
