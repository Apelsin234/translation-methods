package kravchenko.solution;

import com.google.common.base.Joiner;

import java.util.List;

/**
 * Created by Alexander on 10.12.2018.
 */
public class Function implements FormatProgram {

    private final String type;
    private final String name;
    private final List<Argument> parameters;
    private final Block block;


    public Function(final String type,
                    final String name,
                    final List<Argument> parameters,
                    final Block block) {
        this.type = type;
        this.name = name;
        this.parameters = parameters;
        this.block = block;
    }

    @Override
    public void getFormatProgram(final StringBuilder builder, final int tabs) {
        builder.append(type).append(" ").append(name).append('(');
        if (parameters != null && !parameters.isEmpty()) {
            builder.append(Joiner.on(", ").join(parameters));
        }
        builder.append(") ");
        block.getFormatProgram(builder, tabs);
    }
}
