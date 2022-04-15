package lexing;

public class TokenData {
    int integerValue;
    String identifier;

    //No extra data is required for parsing lexed token
    TokenData() { }

    TokenData(int integerValue) {
        this.integerValue = integerValue;
    }

    TokenData(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getIntegerValue() {
        return integerValue;
    }

}
