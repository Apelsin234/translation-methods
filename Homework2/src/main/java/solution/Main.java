package solution;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Alexander on 04.12.2018.
 */
public class Main {

    private static void toPicture(String fileName, Tree graph) throws IOException {
        Graphviz.fromString("digraph Tree{\n" + graph + "}").render(Format.PNG).toFile(new File(fileName)  );
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        Tree tree = parser.parse(new FileInputStream("Homework2/input.txt"));
        toPicture("Homework2/picture.png", tree);
    }
}
