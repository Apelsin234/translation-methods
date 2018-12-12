package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class AsgnState implements State {

    private final Expression expressionLeft;
    private final Expression expressionRight;

    public AsgnState(Expression expressionLeft, Expression expressionRight) {
        this.expressionLeft = expressionLeft;
        this.expressionRight = expressionRight;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        final String indent = getIndent(tabs);
        //builder.append(indent);
        expressionLeft.getFormatProgram(builder, tabs);
        if (expressionRight != null) {
            builder.append(" = ");
            expressionRight.getFormatProgram(builder, tabs);
        }
        builder.append(";");
    }
}
