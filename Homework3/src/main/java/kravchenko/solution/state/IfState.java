package kravchenko.solution.state;

import kravchenko.solution.expression.Expression;

public class IfState implements State{

    private final Expression expression;
    private final State stateIf;
    private final State stateElse;

    public IfState(Expression expression, State stateIf, State stateElse) {
        this.expression = expression;
        this.stateIf = stateIf;
        this.stateElse = stateElse;
    }


    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        String indent = getIndent(tabs);
        builder/*.append(indent)*/.append("if (");
        expression.getFormatProgram(builder, tabs);
        builder.append(") ");
        stateIf.getFormatProgram(builder, tabs);
        if (stateElse != null) {
            builder.append(" else ");
            stateElse.getFormatProgram(builder, tabs);
        }
    }
}
