package kravchenko.solution.generator;

import kravchenko.solution.utils.LexemRule;
import kravchenko.solution.utils.ParserGrammar;
import kravchenko.solution.utils.TermRule;

import java.util.List;

public class TokenGenerator extends CommonGenerator {

    public TokenGenerator(String dir, String name, ParserGrammar grammar) {
        super(dir, name, name + "Token.java", grammar);
    }


    @Override
    void printImports() {

    }

    @Override
    void printClass() {
        printName();
        printTokens();
        printEnd();
    }

    private void printName() {
        printLine(0, "public enum ", tokenName, " {");
    }

    private void printEnd() {
        printLine(0, "}");
    }

    private void printTokens() {
        List<LexemRule> lexems = grammar.lexems;
        for (LexemRule rule : lexems) {
            printLine(1, rule.name, ",");
        }
        printLine(1, "END");
    }

}
