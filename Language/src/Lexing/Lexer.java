package Lexing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
    private BufferedReader reader;
    private int lineNumber = 1; //Make proper fixes to these, right now there just here as placeholders
    private int columnNumber = 0;
    private String nextChar;

    Map<String, TokenType> keywords;


    public Lexer(String programSource) throws IOException {
        keywords = new HashMap<>();
        this.reader = new BufferedReader(new FileReader(programSource));
        keywords.put("+", TokenType.PLUS);
        keywords.put("-", TokenType.MINUS);
        keywords.put("*", TokenType.TIMES);
        keywords.put("/", TokenType.DIVIDE);
        keywords.put("=", TokenType.ASSIGNEQ);
        keywords.put(";", TokenType.SEMICOLON);
        nextChar = readNextChar();

    }

    private String readNextChar() throws IOException {
        String val = Character.toString((char) reader.read());
        while (isCharSkippable(val)) {
            columnNumber++;
            val = Character.toString((char) reader.read());
        }
        return val;

    }

    //Add more skippable rules
    private boolean isCharSkippable(String next) {
        if (next.equals(" ")) {
            return true;
        }
        return false;
    }

    public void testStuff() {

    }

    public Token getToken() throws IOException {

        if (isDigit(nextChar)) {
            String acc = nextChar;
            nextChar = readNextChar();
            while (isDigit(nextChar)) {
                acc = acc + nextChar;
                nextChar = readNextChar();
            }
            return new Token(TokenType.INT, new TokenData(Integer.parseInt(acc)), lineNumber, columnNumber);
        }


        switch (nextChar) {
            case "+":
                nextChar = readNextChar();
                return new Token(TokenType.PLUS, new TokenData(), lineNumber, columnNumber);
            case "-":
                nextChar = readNextChar();
                return new Token(TokenType.MINUS, new TokenData(), lineNumber, columnNumber);
            case "*":
                nextChar = readNextChar();
                return new Token(TokenType.TIMES, new TokenData(), lineNumber, columnNumber);
            case "/":
                nextChar = readNextChar();
                return new Token(TokenType.DIVIDE, new TokenData(), lineNumber, columnNumber);
            case ";":
                nextChar = readNextChar();
                return new Token(TokenType.SEMICOLON, new TokenData(), lineNumber, columnNumber);


            default:
                return new Token(TokenType.EOF, new TokenData(), lineNumber, columnNumber);

        }
    }


    private boolean isDigit(String str) {
        if (str == null) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
