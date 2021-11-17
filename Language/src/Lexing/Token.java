package Lexing;

public class Token {
    private TokenType type;
    private int lineNumber;
    private int columnNumber;
    private TokenData tokenData;

    public Token(TokenType type, TokenData tokenData, int lineNumber, int columnNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.tokenData = tokenData;
    }

    public TokenType getType() {
        return type;
    }

    public TokenData getTokenData() {
        return tokenData;
    }

    //Primarily for early debugging purposes
    public String toString() {
        if (type.equals(TokenType.INT)) {
            return type.toString() + "(" + tokenData.integerValue + ") " + lineNumber + ":" + columnNumber;
        }

        if (type.equals(TokenType.ID)) {
            return type.toString() + "(" + tokenData.identifier + ") " + lineNumber + ":" + columnNumber;
        }

        return type.toString() + " " +  lineNumber + ":" + columnNumber;
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

}
