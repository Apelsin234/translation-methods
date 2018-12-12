package kravchenko.solution;

import antlr.CPPLexer;
import antlr.CPPParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProgramTest {


    @Test
    public void test1() {
        String code = "int main() {" +
                "   return 0;" +
                "}";
        String answer = "int main() {\n" +
                "    return 0;\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test2() {
        String code = "int main() {\n" +
                "   int x = 5;" +
                "   int y = 7;" +
                "   int z = x + y;" +
                "   int ght = x*z+(x*89)        - 8889" +
                ";" +
                "   return 0;" +
                "}";
        String answer = "int main() {\n" +
                "    int x = 5;\n" +
                "    int y = 7;\n" +
                "    int z = x + y;\n" +
                "    int ght = x * z + (x * 89) - 8889;\n" +
                "    return 0;\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test3() {
        String code = "int main() {\n" +
                "}";
        String answer = "int main() {\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test4() {
        String code = "int main() {\n" +
                "if ( x     >2 ) x = r;" +
                "if (5 ==    5)    { " +
                "yu = 56;" +
                "foo();" +
                "bar(1,2 ,3, true, bar(2,3,4,false, xxx()));" +
                "} if(false  ){q = 1;b=2;} else{x;}}";
        String answer = "int main() {\n" +
                "    if (x > 2) x = r;\n" +
                "    if (5 == 5) {\n" +
                "        yu = 56;\n" +
                "        foo();\n" +
                "        bar(1, 2, 3, true, bar(2, 3, 4, false, xxx()));\n" +
                "    }\n" +
                "    if (false) {\n" +
                "        q = 1;\n" +
                "        b = 2;\n" +
                "    } else {\n" +
                "        x;\n" +
                "    }\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test5() {
        String code = "int main() {\n" +
                "printf(52);" +
                "{34 == 34;" +
                "{ { { double x = 34; } } } }" +
                "}";
        String answer = "int main() {\n" +
                "    printf(52);\n" +
                "    {\n" +
                "        34 == 34;\n" +
                "        {\n" +
                "            {\n" +
                "                {\n" +
                "                    double x = 34;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test6() {
        String code = "int bar(int a, int b,double g, char       tt                , bool po) {\n" +
                "if (a == 1 || b == -305 || !(25 + -36 == 5)) {int k = 90;} " +
                "}";
        String answer = "int bar(int a, int b, double g, char tt, bool po) {\n" +
                "    if (a == 1 || b == -305 || !(25 + -36 == 5)) {\n" +
                "        int k = 90;\n" +
                "    }\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test7() {
        String code = "int main() {\n" +
                "{}{}{}{}{}{}" +
                "}";
        String answer = "int main() {\n" +
                "    {\n" +
                "    }\n" +
                "    {\n" +
                "    }\n" +
                "    {\n" +
                "    }\n" +
                "    {\n" +
                "    }\n" +
                "    {\n" +
                "    }\n" +
                "    {\n" +
                "    }\n" +
                "}";
        run(code, answer);
    }

    @Test
    public void test8() {
        String code = "int main() {\n" +
                "}" +
                "void foo(int a, int b, int c)  { return;}" +
                "int sqr(int x) { return x * x;}";
        String answer = "int main() {\n" +
                "}\n" +
                "\n" +
                "void foo(int a, int b, int c) {\n" +
                "    return;\n" +
                "}\n" +
                "\n" +
                "int sqr(int x) {\n" +
                "    return x * x;\n" +
                "}";
        run(code, answer);
    }

    private void run(String code, String answer) {
        CPPLexer lexer = new CPPLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPPParser parser = new CPPParser(tokens);
        Program program = parser.start().v;
        String formatCode = program.getFormatProgram();
        System.out.println("--------------------------------------------------");
        System.out.println("Before");
        System.out.println(code);
        System.out.println("--------------------------------------------------");
        System.out.println("After");
        System.out.println(formatCode);
        System.out.println("--------------------------------------------------");
        Assert.assertEquals(answer, formatCode);
    }

}
