package AST;


public class Program {
    StatementList statementList;

    public Program(StatementList statementList){
        this.statementList = statementList;
    }

    public String toString() {
        return statementList.toString();
    }

}
