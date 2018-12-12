package kravchenko.solution.expression;

import java.util.List;

public class FunctionCall implements Expression {

    private final String name;
    private final List<Expression> expressions;

    public FunctionCall(String name, List<Expression> expressions) {
        this.name = name;
        this.expressions = expressions;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(name).append('(');
        if (expressions != null) {
            for (int i = 0; i < expressions.size(); i++) {
                expressions.get(i).getFormatProgram(builder, tabs);
                if (i != expressions.size() - 1) {
                    builder.append(", ");
                }
            }
        }
        builder.append(')');
    }
}
