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

    //Primarily for early debugging purposes
    public String toString() {
        if (type.equals(TokenType.INT)) {
            return type.toString() + "(" + tokenData.integerValue + ")";
        }
        return type.toString();
    }


}
