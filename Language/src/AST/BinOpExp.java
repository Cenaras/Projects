package AST;

import Exceptions.IllegalArithmeticException;

public class BinOpExp extends Exp {

    public enum Operator {
        PLUS,
        MINUS,
        TIMES,
        DIVIDE
    }

    Exp left;
    Operator op;
    Exp right;

    public BinOpExp(Exp left, Operator op, Exp right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public int eval() throws IllegalArithmeticException {
        int leftEval = left.eval();
        int rightEval = right.eval();
        int result = 0;
        switch (this.op) {
            case PLUS:
                result = leftEval + rightEval;
                break;
            case MINUS:
                result = leftEval - rightEval;
                break;
            case TIMES:
                result = leftEval * rightEval;
                break;
            case DIVIDE:
                if (rightEval != 0) {
                    result = leftEval / rightEval;
                    break;
                } else {
                    throw new IllegalArithmeticException("Cannot divide by zero!");
                }
        }
        return result;
    }
}
