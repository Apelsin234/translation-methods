package kravchenko.solution.expression;

import kravchenko.solution.Tokens;

public class BinaryExpression implements Expression{

    private final Tokens operation;
    private final Expression expressionLeft;
    private final Expression expressionRight;

    public BinaryExpression(Tokens operation, Expression expressionLeft, Expression expressionRight) {
        this.operation = operation;
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        expressionLeft.getFormatProgram(builder, tabs);
        builder.append(" ").append(operation).append(" ");
        expressionRight.getFormatProgram(builder, tabs);
    }
}
