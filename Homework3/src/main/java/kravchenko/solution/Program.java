package kravchenko.solution;

import java.util.List;

/**
 * Created by Alexander on 10.12.2018.
 */
public class Program {

    private final List<Function> list;

    public Program(final List<Function> list) {
        this.list = list;
    }

    public String getFormatProgram() {
        final StringBuilder builder = new StringBuilder();
        list.forEach(it -> {
            it.getFormatProgram(builder, 0);
            builder.append("\n\n");
        });
        return builder.toString().trim();
    }
}
