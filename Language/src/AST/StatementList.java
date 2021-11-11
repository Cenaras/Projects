package AST;

import Exceptions.IllegalArithmeticException;

import java.util.ArrayList;
import java.util.List;

public class StatementList {
    List<Statement> statemenList;

    public StatementList() {
        statemenList = new ArrayList<>();
    }

    public void addStatement(Statement statement) {
        statemenList.add(statement);
    }

    public String toString()
    {
        String result = "";
        for(Statement s : statemenList) {
            result += s.toString();
        }
        return result;
    }

}
