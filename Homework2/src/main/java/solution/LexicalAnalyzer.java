package solution;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Alexander on 04.12.2018.
 */
public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private String curWord;
    private int curPos;
    private Token curToken;

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    private String nextWord() throws ParseException {
        StringBuilder builder = new StringBuilder();
        while (Character.isLetterOrDigit(curChar)) {
            builder.append((char) curChar);
            nextChar();
        }
        return builder.toString();
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        curWord = null;
        if (Character.isLetter(curChar)) {
            String word = nextWord();
            curWord = word;
            switch (word) {
                case "procedure":
                case "function":
                    curToken = Token.FUNC_OR_PROC;
                    break;
                case "real":
                case "boolean":
                case "integer":
                case "char":
                    curToken = Token.TYPE;
                    break;
                default:
                    curToken = Token.NAME;
            }
            return;
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = Token.RPAREN;
                break;
            case ':':
                nextChar();
                curToken = Token.COLON;
                break;
            case ',':
                nextChar();
                curToken = Token.COMMA;
                break;
            case ';':
                nextChar();
                curToken = Token.SEMICOLON;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal_character " + (char) curChar, curPos);

        }
    }

    public String getWord() {
        return curWord;
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}
