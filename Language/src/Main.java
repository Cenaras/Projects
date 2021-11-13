import AST.Program;
import Lexing.Lexer;
import TypeChecker.Typechecker;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePrefix = "D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\";
        Lexer lexer = new Lexer(filePrefix + "test_files\\" + args[0]);

        lexer.testStuff();
        lexer.getToken();

        //Parser parser = new Parser(filePrefix);

        //Program result = parser.parse(args[0]);
        //Typechecker typechecker = new Typechecker(result);
        //typechecker.analyzeProgram();



    }


}

