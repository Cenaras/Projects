package Parser;

import AST.*;
import Lexing.Lexer;
import Lexing.Token;
import Lexing.TokenType;
import Types.IntegerType;
import Types.Type;

import java.io.IOException;

public class Parser {

    private Lexer lexer;
    private Token nextToken;

    public Parser(String file) throws IOException {
        this.lexer = new Lexer(file);
        nextToken = lexer.getToken();
    }


    //Maybe return a bool, and if we eat then we advance?
    //eat: Check if our next token is equal to the argument. If it is, proceed, else emit error.
    private void eat(TokenType expectedToken) throws IOException {
        if (expectedToken == nextToken.getType()) {
            nextToken = lexer.getToken();
        }
        else {
            emitError(expectedToken);
        }
    }

    private void emitError(TokenType expectedToken) {
        System.out.println("Error at " + nextToken.getLineNumber() + ":" + nextToken.getColumnNumber());
        System.out.println("Expected " + expectedToken + " but got " + nextToken.getType());
    }

    public VarDecl parseVarDecl() throws IOException {
        eat(TokenType.LET);
        IdentifierExp id = parseIdentifier();
        eat(TokenType.ASSIGNEQ);
        Exp value = parseExp();

        return new VarDecl(id, value);

    }

    //Placeholder
    private Type parseType() throws IOException {
        Type type = null;
        if(nextToken.getType() == TokenType.INT) {
            eat(TokenType.INT);
            type = new IntegerType();
        }
        return type;
    }

    private IdentifierExp parseIdentifier() throws IOException {
        String id = "";
        if (nextToken.getType() == TokenType.ID) {
            id = nextToken.getTokenData().getIdentifier();
            eat(TokenType.ID);
        }
        return new IdentifierExp(id);
    }

    //Parse the expression in a left-to-right manner:
    private Exp parseExp() throws IOException {
        Exp base = parseBaseExp();
        return parseExpLeftToRight(base); //Essentially accumulates base into a composite expression

    }

    //For now, only allows single composite expressions
    private Exp parseExpLeftToRight(Exp base) throws IOException {
        TokenType binOp = nextToken.getType();
        eat(binOp);
        Exp right = parseBaseExp();

        switch (binOp) {
            case PLUS:
                base = new BinOpExp(base, "+", right);
                break;
            case MINUS:
                base = new BinOpExp(base, "-", right);
                break;
        }
        return base;
    }

    private Exp parseBaseExp() throws IOException {
        switch (nextToken.getType()) {
            case INT:
                int val = nextToken.getTokenData().getIntegerValue();
                eat(TokenType.INT);
                return new IntExp(val);
            case ID:
                return parseIdentifier();
        }
        return null; //Report error
    }

    //Have a way to distinguish between 2 and 2 + 3
//    private Exp parseExp() throws IOException {
//        switch (nextToken.getType()) {
//            case INT:
//                int val = nextToken.getTokenData().getIntegerValue();
//                eat(TokenType.INT);
//
//        }
//
//
//    }


    /*Idea's: Have a function for each of our non-terminals in our grammar.
    Write out a simple grammar for the start of the language.
    Implement methods to parse the different rules
    * */


    /*  GRAMMAR:
    *
    *   StatementList   := { Statement }
    *
    *   Statement       :=
    *
    *   VarDecl         := LET ID = Exp
    *
    *   Exp             := Exp op Exp
    *                   | INT
    *                   | ID
    *                   | BaseExp
    *
    *   Op              := + | - | * | /
    *
    * */


}
