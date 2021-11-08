package AST;

import Exceptions.IllegalArithmeticException;

public abstract class Exp extends ASTNode{
    public abstract int eval() throws IllegalArithmeticException;

    public abstract String toString();

}
