package parsing;

import AST.VarDecl;
import Parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestParser {

    @Test
    public void testParser() throws IOException {
        String file = "D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\lexing\\test_files\\simple_let";
        Parser parser = new Parser(file);


    }


}
