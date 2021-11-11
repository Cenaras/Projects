package AST;

import Exceptions.IllegalArithmeticException;

public class LetStm implements Statement {

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

}
