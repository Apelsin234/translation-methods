package kravchenko.solution.generator;

import kravchenko.solution.utils.ParserGrammar;

public class MainGenerator {

    public static void generate(String dirOut, String grammarName, ParserGrammar grammar) {
        TokenGenerator tokenGenerator = new TokenGenerator(dirOut, grammarName, grammar);
        tokenGenerator.printFile();
        LexerGenerator lexerGenerator = new LexerGenerator(dirOut, grammarName, grammar);
        lexerGenerator.printFile();
        ParserGenerator parserGenerator = new ParserGenerator(dirOut, grammarName, grammar);
        parserGenerator.printFile();
    }
}
