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

    private Map<String, TokenType> keywords;


    public Lexer(String programSource) throws IOException {
        keywords = new HashMap<>();
        this.reader = new BufferedReader(new FileReader(programSource));
        keywords.put("+", TokenType.PLUS);
        keywords.put("-", TokenType.MINUS);
        keywords.put("*", TokenType.TIMES);
        keywords.put("/", TokenType.DIVIDE);
        keywords.put("=", TokenType.ASSIGNEQ);
        keywords.put(";", TokenType.SEMICOLON);

        keywords.put("let", TokenType.LET);
        nextChar = readNextChar();

    }

    private String readNextChar() throws IOException {
        columnNumber++;
        return Character.toString((char) reader.read());
        // \t is interpreted as a space, so it is implicitly handled by this case for now
    }

    public boolean canSkipChar(String token) {
        return (token.equals(" ")
                || token.equals("\r")
                || token.equals("\n"));
    }



    public void testStuff() {

    }

    public Token getToken() throws IOException {
        //Skip all non-tokenizable entries. If newline, reset columNumber and increment lineNumber
        while (canSkipChar(nextChar)) {
            if (nextChar.equals("\n")) {
                lineNumber++;
                columnNumber = 0;
            } //Set to 0 since we directly increment it afterwards
            nextChar = readNextChar();
        }

        if (isDigit(nextChar)) {
            String acc = nextChar;
            nextChar = readNextChar();
            while (isDigit(nextChar)) {
                acc +=  nextChar;
                nextChar = readNextChar();
            }
            return new Token(TokenType.INT, new TokenData(Integer.parseInt(acc)), lineNumber, columnNumber);
        }

        //Ensure that we actually return stuff at the right time
        if (Character.isLetter(nextChar.charAt(0))) { //Only one char in each String
            String acc = nextChar;
            nextChar = readNextChar();
            while (Character.isLetterOrDigit(nextChar.charAt(0))) {
                acc += nextChar;
                nextChar = readNextChar();
            }
            TokenType typeOfToken = keywords.get(acc);
            if (typeOfToken != null) {
                return new Token(typeOfToken, new TokenData(), lineNumber, columnNumber);
            }
            //Check for other keywords (i.e. true, false, int, ...)
            return new Token(TokenType.ID, new TokenData(acc), lineNumber, columnNumber);
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
            case "=":
                nextChar = readNextChar();
                return new Token(TokenType.ASSIGNEQ, new TokenData(), lineNumber, columnNumber);

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
