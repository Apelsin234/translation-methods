package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class PrintState implements State {

    private final Expression expression;

    public PrintState(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        String indent = getIndent(tabs);
        builder.append("printf(");
        expression.getFormatProgram(builder, tabs);
        builder.append(");");
    }
}
