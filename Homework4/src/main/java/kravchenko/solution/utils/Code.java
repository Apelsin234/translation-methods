package kravchenko.solution.utils;

public class Code implements Pattern {

    public String code;

    public Code(String code) {
        this.code = code.substring(1, code.length() - 1);
    }
}
