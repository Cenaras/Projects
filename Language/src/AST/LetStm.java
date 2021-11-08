package AST;

public class LetStm extends Stm {

    String id;
    int value;
    public LetStm(String id, int value)
    {
        this.id = id;
        this.value = value;
    }

    public String toString() {
        return "LetStm(" + id + " = " + value + ")";
    }
}
