package AST;

import Types.Type;

public class VarDecl {
    private IdentifierExp id;
    private Exp value;

    public VarDecl(IdentifierExp id, Exp value) {
        this.id = id;
        this.value = value;
    }

    public String toString() {
        return "VarDecl(" + id + ", " + value + ")";
    }

}
