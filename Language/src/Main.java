import AST.Program;
import TypeChecker.Typechecker;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePrefix = "D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\";
        Parser parser = new Parser(filePrefix);

        Program result = parser.parse(args[0]);
        Typechecker typechecker = new Typechecker(result);
        typechecker.analyzeProgram();



    }


}

