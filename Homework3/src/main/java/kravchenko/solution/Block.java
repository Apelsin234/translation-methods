package kravchenko.solution;

import com.google.common.base.Strings;
import kravchenko.solution.state.State;

import java.util.List;

public class Block implements FormatProgram {

    private final List<State> states;

    public Block(List<State> states) {
        this.states = states;
    }

    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        builder.append("{\n");
        states.forEach(it -> {
            builder.append(Strings.repeat("    ", tabs + 1));
            it.getFormatProgram(builder, tabs + 1);
            builder.append("\n");
        });
        builder.append(Strings.repeat("    ", tabs)).append("}");

    }
}
