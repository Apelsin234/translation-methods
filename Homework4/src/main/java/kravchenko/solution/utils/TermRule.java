package kravchenko.solution.utils;

import java.util.ArrayList;
import java.util.List;

public class TermRule {
    public String name;
    public String type;
    public List<Argument> args;
    public List<Argument> rets;
    public List<List<Pattern>> rules;

    public TermRule(String name, List<Argument> args, List<Argument> rets) {
        this.name = name;
        this.type = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
        this.args = args;
        this.rets = rets;
        rules = new ArrayList<>();
    }

    public void addRule(List<Pattern> patterns) {
        rules.add(patterns);
    }

    public String getType() {
         return (rets == null) ? "void" : type;
    }


}
