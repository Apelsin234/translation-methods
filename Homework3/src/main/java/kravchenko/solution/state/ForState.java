package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class ForState implements State {

    private final Expression beforeExpression;
    private final Expression middleExpression;
    private final Expression afterExpression;
    private final State state;

    public ForState(Expression beforeExpression, Expression middleExpression, Expression afterExpression, State state) {
        this.beforeExpression = beforeExpression;
        this.middleExpression = middleExpression;
        this.afterExpression = afterExpression;
        this.state = state;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append("for (");
        if (beforeExpression != null) {
            beforeExpression.getFormatProgram(builder, tabs);
        }
        builder.append(";");
        if (middleExpression != null) {
            builder.append(" ");
            middleExpression.getFormatProgram(builder, tabs);
        }
        builder.append(";");
        if (afterExpression != null) {
            builder.append(" ");
            afterExpression.getFormatProgram(builder, tabs);
        }
        builder.append(") ");
        state.getFormatProgram(builder, tabs);

    }
}
