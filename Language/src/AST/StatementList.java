package AST;

import java.util.ArrayList;
import java.util.List;

public class StatementList {
    List<Statement> statementList;

    public StatementList() {
        statementList = new ArrayList<>();
    }

    public void addStatement(Statement statement) {
        statementList.add(statement);
    }

    public String toString()
    {
        String result = "";
        for(Statement s : statementList) {
            result += s.toString();
        }
        return result;
    }

    public List<Statement> getList() {
        return statementList;
    }

    public Statement getStatementAt(int i) {
        return statementList.get(i);
    }

}
