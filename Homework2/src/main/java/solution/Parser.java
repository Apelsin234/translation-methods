package solution;

import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Alexander on 04.12.2018.
 */
public class Parser {
    LexicalAnalyzer lex;

    Tree S() throws ParseException {
        switch (lex.curToken()) {
            case FUNC_OR_PROC:
                // function
                boolean isFunction = lex.getWord().equals("function");
                lex.nextToken();
                // X
                Tree x = X();
                if (isFunction) {
                    // :
                    if (lex.curToken() != Token.COLON) {
                        throw new ParseException(": expected at position", lex.curPos());
                    }
                    lex.nextToken();
                    // T
                    Tree t = T();
                    // END
                    if (lex.curToken() != Token.END) {
                        throw new ParseException("END expected at position", lex.curPos());
                    }
                    return new Tree("S", new Tree("function"), x, new Tree(":"), t);
                } else {
                    if (lex.curToken() != Token.END) {
                        throw new ParseException("END expected at position", lex.curPos());
                    }
                    return new Tree("S", new Tree("procedure"), x);
                }
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree X() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                // name
                Tree n = N();
                // (
                if (lex.curToken() != Token.LPAREN) {
                    throw new ParseException("( expected at position", lex.curPos());
                }
                lex.nextToken();
                // K
                Tree k = K();
                // )
                if (lex.curToken() != Token.RPAREN) {
                    throw new ParseException("( expected at position", lex.curPos());
                }
                lex.nextToken();
                return new Tree("X", n, new Tree("("), k, new Tree(")"));
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree N() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                String word = lex.getWord();
                lex.nextToken();
                return new Tree("N", new Tree(word));
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree K() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                return new Tree("K", Y());
            case RPAREN:
                return new Tree("K");
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree Y() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                //P
                Tree p = P();
                // :
                if (lex.curToken() != Token.COLON) {
                    throw new ParseException(": expected at position but was " + lex.curToken(), lex.curPos());
                }
                lex.nextToken();
                // T
                Tree t = T();
                // Y'
                Tree yPrime = YPrime();
                return new Tree("Y", p, new Tree(":"),t, yPrime);
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree YPrime() throws ParseException {
        switch (lex.curToken()) {
            case SEMICOLON:
                lex.nextToken();
                return new Tree("Y'", new Tree(";"), Y());
            case RPAREN:
                return new Tree("Y'");
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }


    Tree P() throws ParseException {
        switch (lex.curToken()) {
            case NAME:
                // N
                Tree n = N();
                // P'
                return new Tree("P",n, PPrime());
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }

    Tree PPrime() throws ParseException {
        switch (lex.curToken()) {
            case COMMA:
                lex.nextToken();
                return new Tree("P'", new Tree(","), P());
            case COLON:
                return new Tree("P'");
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());
        }
    }


    Tree T() throws ParseException {
        switch (lex.curToken()) {
            case TYPE:
                String word = lex.getWord();
                lex.nextToken();
                return new Tree("T", new Tree(word));
            default:
                throw new AssertionError("Not expected token [" + lex.curToken() + "] at position " + lex.curPos());

        }
    }

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }


}
