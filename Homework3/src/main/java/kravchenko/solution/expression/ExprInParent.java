package kravchenko.solution.expression;

public class ExprInParent implements Expression {

    private final Expression expression;

    public ExprInParent(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append('(');
        expression.getFormatProgram(builder, tabs);
        builder.append(')');
    }
}
