package TypeChecker;

import AST.*;


public class Typechecker  {

    Program untypedProgram;
    StatementList statementList;

    public Typechecker(Program untypedProgram) {
        this.untypedProgram = untypedProgram;
        this.statementList = untypedProgram.getStatementList();
    }

    public void analyzeProgram() {
        Statement test = untypedProgram.getStatementList().getList().get(0);



        System.out.println(test);

    }

}
