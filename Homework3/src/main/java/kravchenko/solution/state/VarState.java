package kravchenko.solution.state;

import kravchenko.solution.Variable;

public class VarState implements State {

    private final Variable variable;

    public VarState(Variable expression) {
        this.variable = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        String indent = getIndent(tabs);
       // builder.append(indent);
        variable.getFormatProgram(builder, tabs);
    }
}
