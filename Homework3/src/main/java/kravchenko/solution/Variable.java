package kravchenko.solution;

import kravchenko.solution.expression.Expression;

public class Variable implements FormatProgram {

    private final String type;
    private final String name;
    private final Expression expression;

    public Variable(String type, String name, Expression expression) {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(type).append(" ").append(name);
        if (expression != null) {
            builder.append(" = ");
            expression.getFormatProgram(builder, tabs);
        }
        builder.append(';');
    }
}
