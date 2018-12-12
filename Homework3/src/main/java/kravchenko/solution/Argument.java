package kravchenko.solution;

public class Argument implements FormatProgram {

    private final String type;
    private final String name;

    public Argument(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append(type).append(" ").append(name);
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
