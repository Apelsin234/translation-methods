package kravchenko.solution.state;

import kravchenko.solution.Block;

public class BlockState implements State {

    private final Block block;
    public BlockState(Block block) {
        this.block = block;
    }


    @Override
    public void getFormatProgram(StringBuilder builder, int tabs) {
        String indent = getIndent(tabs);
        //builder.append(indent);
        block.getFormatProgram(builder, tabs);
    }
}
