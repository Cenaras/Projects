package AST;

public class IntExp extends Exp {

    int value;

    public IntExp(int value) {
        this.value = value;
    }

    public int eval() {
        return value;
    }

    public String toString() {
        return "IntExp(" + value + ")";
    }
}
