package AST;

import Exceptions.IllegalArithmeticException;

public abstract class ASTNode {
    public abstract String toString();
    public abstract int eval() throws IllegalArithmeticException;
}
