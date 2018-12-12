grammar CPP;

@header {
package antlr;

import kravchenko.solution.expression.Expression;
import kravchenko.solution.expression.BinaryExpression;
import kravchenko.solution.expression.ExprInParent;
import kravchenko.solution.expression.FunctionCall;
import kravchenko.solution.expression.Number;
import kravchenko.solution.expression.UnaryExpression;
import kravchenko.solution.expression.VariableName;
import kravchenko.solution.state.State;
import kravchenko.solution.state.AsgnState;
import kravchenko.solution.state.BlockState;
import kravchenko.solution.state.IfState;
import kravchenko.solution.state.PrintState;
import kravchenko.solution.state.ReturnState;
import kravchenko.solution.state.VarState;
import kravchenko.solution.Argument;
import kravchenko.solution.Block;
import kravchenko.solution.Function;
import kravchenko.solution.Program;
import kravchenko.solution.Tokens;
import kravchenko.solution.Variable;

}


start returns [Program v]
    : file {$v = new Program($file.v);} ;

file returns [List<Function> v]
    : {List<Function> functions = new ArrayList<>();}
      (functionDecl {functions.add($functionDecl.v);})+
      {$v = functions;};

functionDecl returns [Function v]
    : TYPE NAME '(' formalParameters? ')' block
     {$v = new Function($TYPE.text, $NAME.text, $formalParameters.ctx == null ? null : $formalParameters.v, $block.v);};

formalParameters returns [List<Argument> v]
    : arg0 = formalParameter        {List<Argument> parameters = new ArrayList<>();}
                                    {parameters.add($arg0.v);}
    (',' argI = formalParameter     {parameters.add($argI.v);})*
    {$v = parameters;}
    ;

formalParameter returns [Argument v]
    : TYPE NAME {$v = new Argument($TYPE.text, $NAME.text);};

block returns [Block v]
    : {List<State> list = new ArrayList<>();}
    '{' (stat {list.add($stat.v);})* '}'
    {$v = new Block(list);};

stat returns [State v]
    :   block   {$v = new BlockState($block.v);}
    |   varDecl {$v = new VarState($varDecl.v);}
    |   'if' '(' expr ')' st1=stat ('else' st2=stat)? {$v = new IfState($expr.v, $st1.v, $st2.ctx == null ? null : $st2.v);}
    |   'return' expr? ';' {$v = new ReturnState($expr.ctx == null ? null : $expr.v);}
    |   'printf' '(' expr ')' ';' {$v = new PrintState($expr.v);}
    |   ex1=expr ('=' ex2=expr )? ';' {$v = new AsgnState($ex1.v, $ex2.ctx == null ? null : $ex2.v);}
    ;

varDecl returns [Variable v]
    : TYPE NAME ('=' expr)? ';' {$v = new Variable($TYPE.text, $NAME.text, $expr.ctx == null ? null : $expr.v);}
    ;

expr returns [Expression v]
    :   un_op expr {$v = new UnaryExpression($un_op.v, $expr.v);}
    |   exprL = expr bin_op exprR = expr {$v = new BinaryExpression($bin_op.v, $exprL.v, $exprR.v);}
    |   NAME '(' exprList? ')' {$v = new FunctionCall($NAME.text, $exprList.ctx == null ? null : $exprList.v);}
    |   NAME {$v = new VariableName($NAME.text);}
    |   NUM  {$v = new Number($NUM.text);}
    |   '(' expr ')' {$v = new ExprInParent($expr.v);}
    ;

exprList returns [List<Expression> v]
    : expr0 = expr         {List<Expression> list = new ArrayList<>();}
                           {list.add($expr0.v);}
     (',' exprI = expr     {list.add($exprI.v);})*
    {$v = list;}
    ;

un_op returns [Tokens v]
    : NOT   {$v = Tokens.NOT;}
    | SUB   {$v = Tokens.SUB;}
    ;
bin_op returns [Tokens v]
    : num_op    {$v = $num_op.v;}
    | num_cmp   {$v = $num_cmp.v;}
    | bool_cmp  {$v = $bool_cmp.v;}
    ;

num_cmp returns [Tokens v]
    : LT    {$v = Tokens.LT;}
    | GT    {$v = Tokens.GT;}
    | LTEQ  {$v = Tokens.LTEQ;}
    | GTEQ  {$v = Tokens.GTEQ;}
    ;
bool_cmp returns [Tokens v]
    : AND   {$v = Tokens.AND;}
    | OR    {$v = Tokens.OR;}
    | EQ    {$v = Tokens.EQ;}
    | NQ    {$v = Tokens.NQ;}
    ;
num_op returns [Tokens v]
    : DIV   {$v = Tokens.DIV;}
    | MUL   {$v = Tokens.MUL;}
    | MOD   {$v = Tokens.MOD;}
    | ADD   {$v = Tokens.ADD;}
    | SUB   {$v = Tokens.SUB;}
    ;


TYPE : 'int' | 'double' | 'bool' | 'char' | 'void';
NAME : ('a' .. 'z' | 'A' .. 'Z') ('a' .. 'z' | 'A' .. 'Z' | '0'..'9' | '_')*;
NUM  : ('-'? ('1'..'9') ('0'..'9')*) | '0';
ADD : '+';
SUB : '-';
DIV : '/';
MUL : '*';
MOD : '%';
STRING : '"' .*? '"';

ASGN   : '=';
NOT    : '!';
AND    : '&&';
OR     : '||';
LT     : '<';
GT     : '>';
LTEQ   : '<=';
GTEQ   : '>=';
EQ     : '==';
NQ     : '!=';

WS  : [ \t\n\r] -> skip;