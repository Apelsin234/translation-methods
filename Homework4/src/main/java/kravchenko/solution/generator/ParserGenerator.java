package kravchenko.solution.generator;

import kravchenko.solution.utils.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParserGenerator extends CommonGenerator{
    public ParserGenerator(String dir, String name, ParserGrammar grammar) {
        super(dir, name, name + "Parser.java", grammar);
    }

    void printImports() {
        printLine(0, "import java.text.ParseException;");
        out.write(N);
    }

    void printClass() {
        printName();
        printFields();
        printReturnClasses();
        printMethodParse();
        printNTermMethods();
        printMethodConsume();
        printEnd();
    }

    private void printName() {
        printLine(0, "public class ", parserName, " {");
    }

    private void printEnd() {
        printLine(0, "}");
    }

    private void printFields() {
        printLine(1, "private ", lexerName, " lex;");
        out.write(N);
    }

    private void printReturnClasses() {
        for (TermRule rule : grammar.terms) {
            if (rule.rets == null) continue;
            printLine(1, "public class ", rule.getType(), " {");
            for (Argument ret : rule.rets) {
                printLine(2,"public ", ret.type, " ", ret.name, ";");
            }
            printLine(1, "}");
            out.write(N);
        }
    }

    private void printMethodParse() {
        String type = grammar.termToType.get(grammar.startPoint);
        printLine(1, "public ", type, " parse(String expr) throws ParseException {");
        printLine(2, "lex = new ", lexerName, "(expr);");
        printLine(2, "lex.nextToken();");
        printLine(2, (!type.equals("void") ? type + " p = " : ""), grammar.startPoint, "();");
        printLine(2, "if (lex.getCurToken() != ", tokenName, ".END) {");
        printLine(3, "throw new AssertionError(lex.getCurToken());");
        printLine(2, "}");

        if (!type.equals("void"))
            printLine(2, "return p;");
        printLine(1, "}");
        out.write(N);
    }

    private void printNTermMethods() {
        List<TermRule> rules = grammar.terms;
        for (TermRule rule : rules) {
            beginNTermMethod(rule);
            if (firstCaseNTermMethod(rule))
                followCaseNTermMethod(rule);
            endNTermMethod();
        }
    }

    private void beginNTermMethod(TermRule rule) {
        StringBuilder builder = new StringBuilder();
        builder.append(TAB).append("private ")
                .append(rule.getType())
                .append(' ')
                .append(rule.name)
                .append('(');

        List<Argument> argList = rule.args;
        if (argList != null) {
            for (int i = 0; i < argList.size(); i++) {
                builder.append(argList.get(i).type)
                        .append(' ')
                        .append(argList.get(i).name);
                if (i != argList.size() - 1)
                    builder.append(", ");
            }
        }

        builder.append(") throws ParseException {")
                .append(N);
        out.write(builder.toString());
        if (!rule.getType().equals("void"))
            printLine(2, rule.getType(), " ret = new ", rule.getType(), "();");
        printLine(2, "switch (lex.getCurToken()) {");
    }

    private boolean firstCaseNTermMethod(TermRule rule) {
        boolean isHaveEps = false;
        label:
        for (List<Pattern> prodList : rule.rules) {
            for (String s : grammar.getFirstList(prodList)) {
                if (s.equals("EPS")) {
                    isHaveEps = true;
                    continue label;
                } else {
                    printLine(3, "case ", s, ":");
                }
            }
            caseBody(rule, prodList);
        }
        return isHaveEps;
    }

    private String substitute(String code) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            builder.append(code.charAt(i) == '$' ? "ret." : code.charAt(i));
        }
        return builder.toString();
    }

    private void caseBody(TermRule rule, List<Pattern> prodList) {
        Set<String> isHaveVar = new HashSet<>();
        printLine(3, "{");
        for (Pattern prod : prodList) {
            if (prod instanceof Code) {
                printLine(4, substitute(((Code) prod).code));
            } else if (prod instanceof Lexem) {
                String name = ((Lexem) prod).name;
                StringBuilder builder = new StringBuilder();

                if (!isHaveVar.contains(name)) {
                    isHaveVar.add(name);
                    builder.append("String ");
                }
                builder.append(name).append(" = consume(")
                        .append(tokenName)
                        .append('.')
                        .append(name)
                        .append(");");

                printLine(4, builder.toString());
            } else {
                String name = ((Term) prod).name;
                StringBuilder builder = new StringBuilder();
                String type = grammar.termToType.get(name);
                if (!type.equals("void")) {
                    if (!isHaveVar.contains(name)) {
                        isHaveVar.add(name);
                        builder.append(type)
                                .append(' ');
                    }
                    builder.append(name)
                            .append(" = ");
                }

                builder.append(name)
                        .append('(');
                List<String> parameters = ((Term) prod).parameters;
                for (int i = 0; i < parameters.size(); i++) {
                    builder.append(parameters.get(i));
                    if (i != parameters.size() - 1)
                        builder.append(", ");

                }

                builder.append(");");
                printLine(4, builder.toString());
            }
        }
        printLine(4, "return", (!rule.getType().equals("void") ? " ret" : ""), ";");
        printLine(3, "}");
    }

    private void followCaseNTermMethod(TermRule rule) {
        if (grammar.getFollow(rule.name).isEmpty()) return;
        for (String s : grammar.getFollow(rule.name)) {
            printLine(3, "case ", s, ":");
        }
        for (List<Pattern> prodList : rule.rules) {
            boolean isHaveEps = false;
            for (String s : grammar.getFirstList(prodList)) {
                if (s.equals("EPS")) {
                    isHaveEps = true;
                    break;
                }
            }
            if (isHaveEps) {
                for (Pattern prod : prodList) {
                    if (prod instanceof Code) {
                        printLine(4, substitute(((Code) prod).code));
                    }
                }
                printLine(4, "return", (!rule.getType().equals("void") ? " ret" : ""), ";");
            }
        }
    }

    private void endNTermMethod() {
        printLine(3, "default:");
        printLine(4, "throw new AssertionError();");
        printLine(2, "}");
        printLine(1, "}");
        out.write(N);
    }

    private void printMethodConsume() {
        printLine(1, "private String consume(", tokenName, " token) throws ParseException {");
        printLine(2, "if (lex.getCurToken() != token) {");
        printLine(3, "throw new ParseException(\"Incorrect token at position: \", lex.getCurPos());");
        printLine(2, "}");
        printLine(2, "String lexeme = lex.getLexeme();");
        printLine(2, "lex.nextToken();");
        printLine(2, "return lexeme;");
        printLine(1, "}");
        out.write(N);
    }
}
