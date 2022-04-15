package lexing;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestLexer {


    @Test
    public void testLexer() throws IOException {
        Lexer lexer = new Lexer("D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\lexing\\test_files\\simple_let");
        List<Token> tokens = lexer.lexFile();
        System.out.println(tokens);
    }


//    private Lexer createLexerFromFile(String filename) {
//        InputStream in = TestLexer.class.getResourceAsStream(filename);
//        return null;
//    }


}