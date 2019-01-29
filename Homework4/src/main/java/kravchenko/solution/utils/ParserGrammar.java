package kravchenko.solution.utils;

import java.util.*;

public class ParserGrammar {

    public String startPoint;
    public List<LexemRule> lexems;
    public List<TermRule> terms;
    public String WS;
    public Map<String, String> termToType;
    public Map<String, Integer> termToNum;
    public Set<String>[] first;
    public Set<String>[] follow;
    public Map<List<Pattern>, Integer> patternToNum;
    public Set<String>[] firstPatternList;

    public ParserGrammar() {
        startPoint = "start";
        lexems = new ArrayList<>();
        terms = new ArrayList<>();
    }

    public void addTermRule(TermRule termRule) {
        this.terms.add(termRule);
    }

    public void addLexemRule(LexemRule lexemRule) {
        this.lexems.add(lexemRule);
    }

    public void setWS(String ws) {
        this.WS = ws;
    }

    public Set<String> getFirstList(List<Pattern> patterns) {
        return firstPatternList[patternToNum.get(patterns)];
    }

    public Set<String> getFollow(String term) {
        return follow[termToNum.get(term)];
    }

    public void buildFirstAndFollow() {
        buildTermToType();
        buildFirstSet();
        buildFollowSet();
    }


    private int buildTermToNum() {
        termToNum = new HashMap<>();
        int ind = 0;
        for (TermRule rule : terms) {
            termToNum.put(rule.name, ind++);
        }
        return ind;
    }

    private void buildFollowSet() {
        int size = first.length;
        follow = new HashSet[size];
        for (int i = 0; i < size; i++) {
            follow[i] = new HashSet<>();
        }
        boolean ok = true;
        follow[termToNum.get(startPoint)].add("END");

        while (ok) {
            ok = false;
            for (TermRule term : terms) {
                int id = termToNum.get(term.name);
                for (List<Pattern> patterns : term.rules) {
                    for (int i = 0; i < patterns.size(); i++) {
                        Pattern p = patterns.get(i);
                        if (p instanceof Term) {
                            int patId = termToNum.get(((Term) p).name);
                            if (addNextPattern(i + 1, patterns, patId, id)) {
                                ok = true;
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean addNextPattern(int ind, List<Pattern> patterns, int patId, int startId) {
        boolean ok = false;
        boolean isHaveEps = false;
        for (int i = ind; i < patterns.size(); i++) {
            Pattern p = patterns.get(i);
            if (p instanceof Code) continue;
            if (p instanceof Lexem) {
                String patName = ((Lexem) p).name;
                if (follow[patId].add(patName)) {
                    ok = true;
                }
                break;
            } else {
                int nextId = termToNum.get(((Term) p).name);
                boolean isContinue = false;
                for (String s : first[nextId]) {
                    if (s.equals("EPS")) {
                        isContinue = true;
                        isHaveEps = true;
                    } else if (follow[patId].add(s))
                        ok = true;
                }
                if (!isContinue) break;
            }
        }
        if (isHaveEps || isLast(ind, patterns))
            if (follow[patId].addAll(follow[startId]))
                ok = true;
        return ok;
    }

    private boolean isLast(int ind, List<Pattern> patterns) {
        if (ind == patterns.size()) return true;
        for (int i = ind; i < patterns.size(); i++) {
            if (!(patterns.get(i) instanceof Code))
                return false;
        }
        return true;
    }


    private void buildFirstSet() {
        int size = buildTermToNum();
        first = new HashSet[size];
        for (int i = 0; i < size; i++) {
            first[i] = new HashSet<>();
        }
        size = buildPatternToNum();
        firstPatternList = new HashSet[size];

        for (int i = 0; i < size; i++) {
            firstPatternList[i] = new HashSet<>();
        }
        boolean ok = true;
        while (ok) {
            ok = false;
            for (TermRule term : terms) {
                int id = termToNum.get(term.name);
                for (List<Pattern> patterns : term.rules) {
                    int listId = patternToNum.get(patterns);
                    boolean isEps = true;
                    for (Pattern pattern : patterns) {
                        if (pattern instanceof Code) {
                            continue;
                        }
                        if (pattern instanceof Lexem) {
                            if (first[id].add(((Lexem) pattern).name)) {
                                ok = true;
                            }
                            firstPatternList[listId].add(((Lexem) pattern).name);
                        } else {
                            int sId = termToNum.get(((Term) pattern).name);
                            if (first[id].addAll(first[sId])) {
                                ok = true;
                            }

                            firstPatternList[listId].addAll(first[sId]);
                        }
                        isEps = false;
                        break;
                    }
                    if (isEps) {
                        if (first[id].add("EPS")) {
                            ok = true;
                            firstPatternList[listId].add("EPS");
                        }
                    }
                }

            }
        }

    }

    private int buildPatternToNum() {
        patternToNum = new HashMap<>();
        int ind = 0;
        for (TermRule term : terms) {
            for (List<Pattern> p : term.rules) {
                patternToNum.put(p, ind++);
            }
        }
        return ind;
    }

    private void buildTermToType() {
        termToType = new HashMap<>();
        for (TermRule rule : terms) {
            termToType.put(rule.name, rule.getType());
        }
    }
}
