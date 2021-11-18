package Parser;

import AST.*;
import Lexing.Lexer;
import Lexing.Token;
import Lexing.TokenType;
import Types.IntegerType;
import Types.Type;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private Lexer lexer;
    private Token nextToken;
    private Map<TokenType, Integer> precedence; //Higher level gives greater precedence

    public Parser(String file) throws IOException {
        this.lexer = new Lexer(file);
        nextToken = lexer.getToken();
        precedence = new HashMap<>();
        precedence.put(TokenType.PLUS, 1);
        precedence.put(TokenType.MINUS, 1);
        precedence.put(TokenType.TIMES, 2);
        precedence.put(TokenType.DIVIDE, 2);
    }


    //Maybe return a bool, and if we eat then we advance?
    //eat: Check if our next token is equal to the argument. If it is, proceed, else emit error.
    private void eat(TokenType expectedToken) throws IOException {
        if (expectedToken == nextToken.getType()) {
            nextToken = lexer.getToken();
        } else {
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
        if (nextToken.getType() == TokenType.INT) {
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
            return new IdentifierExp(id);
        }
        throw new IOException();
    }

    //Parse the expression in a left-to-right manner:
    private Exp parseExp() throws IOException {
        Exp base = parseBaseExp();
        return parseExpLeftToRight(base, 0); //Essentially accumulates base into a composite expression
    }

    //https://en.wikipedia.org/wiki/Operator-precedence_parser

    private Exp parseExpLeftToRight(Exp base, int minPrecedence) throws IOException {
        TokenType lookahead = nextToken.getType();
        while (precedence.getOrDefault(lookahead, -1) >= minPrecedence ) {
            TokenType op = nextToken.getType();
            eat(op);
            Exp right = parseBaseExp();
            lookahead = nextToken.getType();
            while (precedence.getOrDefault(lookahead, -1) > precedence.get(op)) { //If right associative then >= else just >: When adding ^ to language, get a "isRightAssoc()" and make a || case with >=
                 right = parseExpLeftToRight(right, precedence.get(op) + 1);
                 lookahead = nextToken.getType();
            }
            base = new BinOpExp(base, op, right);
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
