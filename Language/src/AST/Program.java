package AST;


import Visitor.Visitor;

import java.util.List;

public class Program {
    StatementList statementList;

    public Program(StatementList statementList){
        this.statementList = statementList;
    }

    public String toString() {
        return statementList.toString();
    }

    public StatementList getStatementList() {
        return statementList;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }


}
