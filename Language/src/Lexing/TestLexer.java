package Lexing;

import java.io.IOException;

public class TestLexer {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer("D:\\CodingStuff\\Projects\\Projects\\Language\\tests\\test_files\\simple_lexing_test");



        Token token;
        do {
            token = lexer.getToken();
            System.out.println(token);
        } while (token.getType() != TokenType.EOF);


    }
}
