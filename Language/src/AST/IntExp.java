package AST;

public class IntExp implements Exp {

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
