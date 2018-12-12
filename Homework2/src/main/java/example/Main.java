package example;

import java.io.FileInputStream;

/**
 * Created by Alexander on 04.12.2018.
 */
public class Main {

    private static void watchTree(Tree tree, String s) {
        System.out.print(tree.node);
        if (tree.children != null)
            for (Tree child : tree.children) {
                watchTree(child, s + " ");
            }
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        Tree tree = parser.parse(new FileInputStream("input.txt"));
        watchTree(tree, "");
    }
}
