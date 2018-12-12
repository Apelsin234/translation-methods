package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class ReturnState implements State {

    private final Expression expression;

    public ReturnState(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        String indent = getIndent(tabs);
        builder/*.append(indent)*/.append("return");
        if (expression != null) {
            builder.append(" ");
            expression.getFormatProgram(builder, tabs);
        }
        builder.append(';');

    }
}
