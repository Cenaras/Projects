package AST;

import Exceptions.IllegalArithmeticException;

public class VarExp extends Exp {

    String id;
    public VarExp(String id)
    {
        this.id = id;
    }


    @Override
    public int eval() throws IllegalArithmeticException {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
