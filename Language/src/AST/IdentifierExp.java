package AST;

public class IdentifierExp {
    String id;
    public IdentifierExp(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public String toString()
    {
        return "IdentifierExp(" + id + ")";
    }
}
