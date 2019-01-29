package kravchenko.solution;

import org.testng.Assert;
import org.testng.annotations.Test;
import result.xxx.CalcParser;

import java.text.ParseException;

public class CalculatorTest {
    private CalcParser parser = new CalcParser();

    @Test
    public void test1() throws ParseException {
        String[] test= {
                "5",
                "2 -    8 + 3 ^ 5/20 * 8 - 4                   "
        };
        Assert.assertEquals(parser.parse(test[0]).v, 5);
        Assert.assertEquals(parser.parse(test[1]).v, 86);
    }


}
