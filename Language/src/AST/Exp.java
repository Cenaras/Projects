package AST;


import Types.Type;
import Visitor.Visitor;

public interface Exp {
    public void accept(Visitor v);

}
