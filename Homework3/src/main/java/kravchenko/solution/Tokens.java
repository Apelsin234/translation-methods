package kravchenko.solution;

public enum Tokens {
    MOD("%") ,
    NOT("!"),
    SUB("-"),
    DIV("/"),
    ADD("+"),
    MUL("*"),
    GT(">"),
    GTEQ(">="),
    LT("<"),
    LTEQ("<="),
    AND("&&"),
    OR("||"),
    EQ("=="),
    NQ("!=");

    String text;

    Tokens(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
