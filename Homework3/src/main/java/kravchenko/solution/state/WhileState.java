package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class WhileState implements State {

    private final Expression expression;
    private final State state;

    public WhileState(Expression expression, State state) {
        this.expression = expression;
        this.state = state;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append("while (");
        expression.getFormatProgram(builder, tabs);
        builder.append(") ");
        state.getFormatProgram(builder, tabs);
    }
}
