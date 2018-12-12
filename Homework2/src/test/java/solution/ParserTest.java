package solution;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.*;

/**
 * Created by Alexander on 05.12.2018.
 */
public class ParserTest {
    private Parser parser = new Parser();;

    private InputStream strToStream(final String str) {
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }

    // POSITIVE TESTS

    @Test
    public void functionWithoutArgs() throws Exception{
        InputStream stream = strToStream("function abc(): real");
        parser.parse(stream);
    }

    @Test
    public void procedureWithoutArgs() throws Exception{
        InputStream stream = strToStream("procedure abc123x2()");
        parser.parse(stream);
    }

    @Test
    public void functionWithOneArg() throws Exception{
        InputStream stream = strToStream("function abc(x: real): integer");
        parser.parse(stream);
    }

    @Test
    public void oneTypeManyArgs() throws Exception{
        InputStream stream = strToStream("function abc(a, b, c: integer): real");
        parser.parse(stream);
    }

    @Test
    public void twoTypesManyArgs() throws Exception{
        InputStream stream = strToStream("function abc(a, b, c: integer; x,y,z: real): real");
        parser.parse(stream);
    }

    @Test
    public void manyTypes() throws Exception{
        InputStream stream = strToStream("function abc(a, b, c: integer; x,y,z: real; ty:char; qw:boolean; gt:char): real");
        parser.parse(stream);
    }

    // NEGATIVE TESTS

    @Test
    public void useReserveWordInName() throws Exception{
        InputStream stream = strToStream("procedure function()");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void useReserveWordInName2() throws Exception{
        InputStream stream = strToStream("procedure uunction(real: char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void notColon() throws Exception{
        InputStream stream = strToStream("procedure uunction(y char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void unknownType() throws Exception{
        InputStream stream = strToStream("procedure uunction(y: array)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void notParentheses() throws Exception{
        InputStream stream = strToStream("procedure uunction(y: char");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void notParentheses2() throws Exception{
        InputStream stream = strToStream("procedure uunction y: char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void nameStartWithDigit() throws Exception{
        InputStream stream = strToStream("procedure 7unction(y: char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void extraSymbols() throws Exception{
        InputStream stream = strToStream("procedure unction abc(y: char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void notSemicolon() throws Exception{
        InputStream stream = strToStream("procedure abc(y: char yt: integer)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void noComma() throws Exception{
        InputStream stream = strToStream("procedure abc(y f: char)");
        assertThrows(() -> parser.parse(stream));
    }

    @Test
    public void symbolsAfterEnd() throws Exception{
        InputStream stream = strToStream("procedure abc(y,f: char)                       io");
        assertThrows(() -> parser.parse(stream));
    }
}