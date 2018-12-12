package kravchenko.solution.expression;

public class Number implements Expression {

    private final String value;

    public Number(String value) {
        this.value = value;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(value);
    }
}
