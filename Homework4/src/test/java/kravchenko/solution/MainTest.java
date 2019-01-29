package kravchenko.solution;

import antlr.ParserLexer;
import antlr.ParserParser;
import kravchenko.solution.generator.LexerGenerator;
import kravchenko.solution.generator.MainGenerator;
import kravchenko.solution.generator.ParserGenerator;
import kravchenko.solution.generator.TokenGenerator;
import kravchenko.solution.utils.ParserGrammar;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainTest {

    public static void main(String[] args) throws IOException {
        create("Calc");
    }

    private static void create(String grammarName) throws IOException {
        String dirIn = "Homework4/src/test/resources/" + grammarName;
        if (Files.notExists(Paths.get(dirIn))) {
            System.exit(-1);
        }
        Path path = Paths.get("Homework4/src/main/java/result/xxx");
        Path dir;
        if (Files.notExists(path)) {
            dir = Files.createDirectory(path);
        } else {
            dir = path;
        }
        String dirOut = dir.toString();
        ParserLexer lexer = new ParserLexer(CharStreams.fromFileName(dirIn));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ParserParser parser = new ParserParser(tokens);

        ParserGrammar grammar = parser.start().g;
        grammar.buildFirstAndFollow();
        MainGenerator.generate(dirOut, grammarName, grammar);

    }
}
