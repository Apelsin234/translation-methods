package example;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander on 04.12.2018.
 */
public class Tree {
    String node;

    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}
