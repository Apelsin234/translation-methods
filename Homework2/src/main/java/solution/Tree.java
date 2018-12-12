package solution;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexander on 04.12.2018.
 */
public class Tree {
    static int globalId = 0;
    String node;
    int id;

    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
        id = globalId++;
    }

    public Tree(String node) {
        this.node = node;
        id = globalId++;
    }

    private String help1() {
        StringBuilder curNode = new StringBuilder("\"" + id + "\" [label=\"" + node + "\"];\n");
        if (children == null) {
            return curNode.toString();
        }
        for (Tree child : children) {
            curNode.append(child.help1());
        }
        return curNode.toString();
    }

    private String help2() {
        StringBuilder str = new StringBuilder();
        if (children == null) {
            return "";
        }
        for (Tree child : children) {
            str.append("\"" + id + "\"->\"" + child.id + "\";\n");
        }
        for (Tree child : children) {
            str.append(child.help2());
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return help1() + "\n" + help2();
    }
}
