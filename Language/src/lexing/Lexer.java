package lexing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private BufferedReader reader;
    private int lineNumber = 1; //Make proper fixes to these, right now there just here as placeholders
    private int columnNumber = 0;
    private String currentChar;

    private final Map<String, TokenType> keywords;


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
        currentChar = readNextChar();

    }

    public List<Token> lexFile() throws IOException {
        List<Token> tokenList = new ArrayList<>();
        Token token;
        do {
            token = getToken();
            tokenList.add(token);
        } while (token.getType() != TokenType.EOF);
        return tokenList;
    }


    private String readNextChar() throws IOException {
        columnNumber++;
        return Character.toString((char) reader.read()); //use exception converter instead to get rid of exception
        // \t is interpreted as a space, so it is implicitly handled by this case for now
    }

    /**
     * Checks if current char can be skipped. Space, new lines and likewise can be skipped.
     *
     * @param token current element
     * @return whether the char can be skipped or not
     */
    private boolean canSkipChar(String token) {
        return (token.equals(" ")
                || token.equals("\r")
                || token.equals("\n"));
    }



    public void testStuff() {

    }

    public Token getToken() throws IOException {
        //Skip all non-tokenizable entries. If newline, reset columNumber and increment lineNumber
        while (canSkipChar(currentChar)) {
            if (currentChar.equals("\n")) {
                lineNumber++;
                columnNumber = 0;
            } //Set to 0 since we directly increment it afterwards
            currentChar = readNextChar();
        }

        /* If currentChar is a digit, keep adding digits until no more digits are found to get entire INT */
        if (isDigit(currentChar)) {
            StringBuilder acc = new StringBuilder(currentChar);
            currentChar = readNextChar();
            while (isDigit(currentChar)) {
                acc.append(currentChar);
                currentChar = readNextChar();
            }
            return new Token(TokenType.INT, new TokenData(Integer.parseInt(acc.toString())), lineNumber, columnNumber);
        }

        //Ensure that we actually return stuff at the right time
        if (Character.isLetter(currentChar.charAt(0))) { //Only one char in each String
            StringBuilder acc = new StringBuilder(currentChar);
            currentChar = readNextChar();
            while (Character.isLetterOrDigit(currentChar.charAt(0))) {
                acc.append(currentChar);
                currentChar = readNextChar();
            }
            TokenType typeOfToken = keywords.get(acc.toString());
            if (typeOfToken != null) {
                return new Token(typeOfToken, new TokenData(), lineNumber, columnNumber);
            }
            //Check for other keywords (i.e. true, false, int, ...)
            return new Token(TokenType.ID, new TokenData(acc.toString()), lineNumber, columnNumber);
        }

        switch (currentChar) {
            case "+":
                currentChar = readNextChar();
                return new Token(TokenType.PLUS, new TokenData(), lineNumber, columnNumber);
            case "-":
                currentChar = readNextChar();
                return new Token(TokenType.MINUS, new TokenData(), lineNumber, columnNumber);
            case "*":
                currentChar = readNextChar();
                return new Token(TokenType.TIMES, new TokenData(), lineNumber, columnNumber);
            case "/":
                currentChar = readNextChar();
                return new Token(TokenType.DIVIDE, new TokenData(), lineNumber, columnNumber);
            case ";":
                currentChar = readNextChar();
                return new Token(TokenType.SEMICOLON, new TokenData(), lineNumber, columnNumber);
            case "=":
                currentChar = readNextChar();
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
