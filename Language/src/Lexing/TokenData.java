package Lexing;

public class TokenData {
    int integerValue;
    String stringValue;

    //No extra data is required for parsing lexed token
    TokenData() { }

    TokenData(int integerValue) {
        this.integerValue = integerValue;
    }
}
