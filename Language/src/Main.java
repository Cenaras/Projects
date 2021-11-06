import AST.BinOpExp;
import AST.Exp;
import AST.IntExp;
import Exceptions.IllegalArithmeticException;

public class Main {

    public static void main(String[] args) throws IllegalArithmeticException {
        Exp test = new BinOpExp(new IntExp(2), BinOpExp.Operator.PLUS, new IntExp(4));
        System.out.println(test.eval());
    }
}
