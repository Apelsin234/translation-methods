package kravchenko.solution.utils;

public class LexemRule {

    public String name;
    public String val;
    public boolean isRegexp;

    public LexemRule(String name, String val, boolean isRegexp) {
        this.name = name;
        this.val = val;
        this.isRegexp = isRegexp;
    }

    public boolean isRegexp() {
        return isRegexp;
    }
}
