package kravchenko.solution.generator;

import com.google.common.base.Strings;
import kravchenko.solution.utils.ParserGrammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public abstract class CommonGenerator {

    public String packageName;
    public String tokenName;
    public String lexerName;
    public String parserName;
    public ParserGrammar grammar;
    public PrintWriter out;
    final static String N = System.lineSeparator();
    final static String TAB = "    ";



    public CommonGenerator(String dir, String name, String fileName, ParserGrammar grammar) {
        try {
            out = new PrintWriter(new File(dir, fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.packageName = "result.xxx";
        this.tokenName = name + "Token";
        this.lexerName = name + "Lexer";
        this.parserName = name + "Parser";
        this.grammar = grammar;
    }

    public void printFile() {
        printPackage();
        printImports();
        printClass();
        out.close();
    }

    void printPackage() {
        printLine(0, "package ", packageName, ";");
        out.write(N);
    }

    abstract void printImports();

    abstract void printClass();

    void printLine(int tabCount, String... strings) {
        StringBuilder builder = new StringBuilder();
        builder.append(Strings.repeat(TAB, tabCount));
        for (String str : strings) {
            builder.append(str);
        }
        builder.append(N);
        out.write(builder.toString());
    }
}

