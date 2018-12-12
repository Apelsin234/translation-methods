package kravchenko.solution.expression;

public class VariableName implements Expression {

    private final String name;

    public VariableName(String name) {
        this.name = name;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(name);
    }
}
