package kravchenko.solution.expression;

public class ForDecl implements Expression {

    private final String type;
    private final String name;
    private final Expression expression;

    public ForDecl(String type, String name, Expression expression) {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        if (type != null) {
            builder.append(type).append(' ');
        }
        builder.append(name);

        if (expression != null) {
            builder.append(" = ");
            expression.getFormatProgram(builder, tabs);
        }
    }
}
