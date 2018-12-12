package kravchenko.solution.expression;

import kravchenko.solution.Tokens;

public class UnaryExpression implements Expression{

    private final Tokens operation;
    private final Expression expression;

    public UnaryExpression(Tokens operation, Expression expression) {
        this.operation = operation;
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(operation);
        expression.getFormatProgram(builder, tabs);
    }
}
