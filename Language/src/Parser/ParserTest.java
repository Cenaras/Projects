package Parser;

import AST.VarDecl;

import java.io.IOException;

public class ParserTest {
    public static void main(String[] args) throws IOException {
        String file = "D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\lexing\\test_files\\simple_let";
        Parser parser = new Parser(file);

        VarDecl test = parser.parseVarDecl();
        System.out.println(test);

    }
}
