package kravchenko.solution.utils;

import java.util.ArrayList;
import java.util.List;

public class Term implements Pattern {

    public String name;
    public List<String> parameters;

    public Term(String name) {
        this.name = name;
        this.parameters = new ArrayList();
    }

    public void addParam(String param) {
        parameters.add(param);
    }
}
