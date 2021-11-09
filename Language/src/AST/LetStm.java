package AST;

import Exceptions.IllegalArithmeticException;

public class LetStm extends Stm {

    String id;
    Exp exp;
    public LetStm(String id, Exp exp)
    {
        this.id = id;
        this.exp = exp;
    }

    public String toString() {
        return "LetStm(" + id + "=" + exp.toString() + ")";
    }

    @Override
    public int eval() throws IllegalArithmeticException {
        return 0;
    }
}
