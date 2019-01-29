grammar Parser;

@header {
package antlr;

import kravchenko.solution.utils.*;
}

start returns [ParserGrammar g]
    : {$g = new ParserGrammar();}
       (rooles[$g] ';')+
    ;

rooles[ParserGrammar g]
    : LEXEM '=' STRING {$g.addLexemRule(new LexemRule($LEXEM.text, $STRING.text, false));}
    | LEXEM ':' STRING {$g.addLexemRule(new LexemRule($LEXEM.text, $STRING.text, true));}
    | term             {$g.addTermRule($term.v);}
    | 'WS' '->' STRING {$g.setWS($STRING.text);}
    ;

term returns [TermRule v]
    :  TERM args ret ':' {$v = new TermRule($TERM.text, $args.v, $ret.v);}
        rules {$v.addRule($rules.v);}('|' rules {$v.addRule($rules.v);})*
     ;

args returns [List<Argument> v]
    : {$v = new ArrayList();}
    '['arg {$v.add($arg.v);} (',' arg {$v.add($arg.v);})*']'
    | {$v = null;}
    ;
arg returns [Argument v]
    : n1=name n2=name {$v = new Argument($n1.v, $n2.v);}
    ;

ret returns [List<Argument> v]
    : 'returns' {$v = new ArrayList(); } '[' arg {$v.add($arg.v);} (',' arg {$v.add($arg.v);})* ']'
    | {$v = null;}
    ;
rules returns [List<Pattern> v]
    : {$v = new ArrayList();}
       (rule_q {$v.add($rule_q.v);})+
    ;

rule_q returns [Pattern v]
    : LEXEM {$v = new Lexem($LEXEM.text);}
    | TERM {Term term = new Term($TERM.text);}
    ('['param {term.addParam($param.v);} (',' param {term.addParam($param.v);})*']')? {$v = term;}
    | CODE {$v = new Code($CODE.text);}
    ;

param returns [String v]
    : name {$v = $name.v;}
    | CODE {$v = $CODE.text.substring(1, $CODE.text.length() - 1);}
    ;

name returns [String v]
    : LEXEM {$v = $LEXEM.text;}
    | TERM {$v = $TERM.text;}
    ;

LEXEM : [A-Z][a-z0-9A-Z_]*;
TERM : [a-z][a-z0-9A-Z_]* ;



STRING : '"'(~('\r' | '\n' | '"'))*'"';
CODE   : '{' (~[{}]+ CODE?)* '}';

WS     : [ \t\r\n] -> skip ;