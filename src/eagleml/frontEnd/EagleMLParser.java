/* EagleMLParser.java */
/* Generated By:JavaCC: Do not edit this line. EagleMLParser.java */
package eagleml.frontEnd;

import eagleml.ast.EagleMLAst.*;
import eagleml.types.EagleMLTypes.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

final public class EagleMLParser implements EagleMLParserConstants {
  public void initExpressionStacks() {
    m_operators = new ArrayDeque<Operator>();
    m_operands  = new ArrayDeque<ExprAst>();

    return;
  }

  private void popOperator() {
    final Operator oper = m_operators.pop();

    if (oper.isBinary()) {
      final ExprAst t1 = m_operands.pop();
      final ExprAst t0 = m_operands.pop();

      m_operands.push(BinOper.create(t0, oper, t1));
    }
    else if (oper.isUnary()) {
      final ExprAst t0 = m_operands.pop();
      m_operands.push(UnaryOper.create(oper, t0));
    }
    else
    {
      throw new RuntimeException("Bad operator.");
    }

    return;
  }

  private void pushOperator(final Operator oper) {
    while (m_operators.peek().greaterPrecedence(oper)) {
      popOperator();
    }
    m_operators.push(oper);

    return;
  }

  private Deque<Operator> m_operators;
  private Deque<ExprAst> m_operands;

/********************************************
 * THE EagleML LANGUAGE GRAMMAR STARTS HERE *
 ********************************************/

/*
 * Program structuring syntax follows.
 */
  final public 
List<Def> CompilationUnit() throws ParseException {List<Def> defList;
    defList = CompilationUnitAux();
    jj_consume_token(0);
{if ("" != null) return defList;}
    throw new Error("Missing return statement in function");
  }

  final public List<Def> CompilationUnitAux() throws ParseException {List<Def> defList = new DefinitionList();
  Def def;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VAL:{
        def = VariableDefinition();
        break;
        }
      case DEF:{
        def = FunctionDefinition();
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
defList.add(def);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case DEF:
      case VAL:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
    }
{if ("" != null) return defList;}
    throw new Error("Missing return statement in function");
  }

  final public VarDef VariableDefinition() throws ParseException {String varName;
  EagleMLType varTyp;
  ExprAst expr;
    jj_consume_token(VAL);
    jj_consume_token(IDENTIFIER);
varName = token.image;
    jj_consume_token(COLON);
    varTyp = Type();
    jj_consume_token(ASSIGN);
    expr = Expr();
{if ("" != null) return VarDef.create(varName, varTyp, expr);}
    throw new Error("Missing return statement in function");
  }

  final public FunDef FunctionDefinition() throws ParseException {String funName;
   List<TypedVar> tyVars;
   ExprAst expr;
   EagleMLType funType = null;
    jj_consume_token(DEF);
    jj_consume_token(IDENTIFIER);
funName = token.image;
    jj_consume_token(LPAREN);
    tyVars = FunArgs();
    jj_consume_token(RPAREN);
    jj_consume_token(COLON);
    funType = Type();
    jj_consume_token(ASSIGN);
    expr = Expr();
{if ("" != null) return FunDef.create(funName, tyVars, funType, expr);}
    throw new Error("Missing return statement in function");
  }

  final public List<TypedVar> FunArgs() throws ParseException {TypedVar typedVar;
  List<TypedVar> typedVars = new TypedVarList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IDENTIFIER:{
      typedVar = FormalParameter();
typedVars.add(typedVar);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[2] = jj_gen;
          break label_2;
        }
        jj_consume_token(COMMA);
        typedVar = FormalParameter();
typedVars.add(typedVar);
      }
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      ;
    }
{if ("" != null) return typedVars;}
    throw new Error("Missing return statement in function");
  }

  final public TypedVar FormalParameter() throws ParseException {EagleMLType typ;
  String name;
    jj_consume_token(IDENTIFIER);
name = token.image;
    jj_consume_token(COLON);
    typ = Type();
{if ("" != null) return TypedVar.create(name, typ);}
    throw new Error("Missing return statement in function");
  }

  final public EagleMLType Type() throws ParseException {EagleMLType typ;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
typ = IntPrimitiveType.create();
      break;
      }
    case BOOLEAN:{
      jj_consume_token(BOOLEAN);
typ = BoolPrimitiveType.create();
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return typ;}
    throw new Error("Missing return statement in function");
  }

/*
 * Expression syntax follows.
 */
  final public 
ExprAst Expr() throws ParseException {int operatorStackSize, operandStackSize;
operatorStackSize = m_operators.size(); // Only used in the assert below.
    operandStackSize  = m_operands.size();  // Only used in the assert below.

    m_operators.push(Operator.NO_OPPER);
    ExprAstAux();
final ExprAst res = m_operands.pop();
    final Operator oper = m_operators.pop(); // Get rid of the sentinel.

    assert oper == Operator.NO_OPPER:
           "Sentinel operator was expected but was not found.";
    assert m_operators.size() == operatorStackSize:
           "The expression operands array was not empty at the end of an expression parse.";
    assert m_operands.size() == operandStackSize:
           "The expression operators array was not empty at the end of an expression parse.";

    {if ("" != null) return res;}
    throw new Error("Missing return statement in function");
  }

  final public void ExprAstAux() throws ParseException {Operator oper;
    P();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case GT:
      case LT:
      case EQ:
      case NEQ:
      case LEQ:
      case GEQ:
      case OR:
      case AND:
      case PLUS:
      case MINUS:
      case TIMES:
      case DIVIDE:
      case XOR:
      case REM:{
        ;
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      oper = BinaryOperator();
pushOperator(oper);
      P();
    }
while (! m_operators.peek().isNoOper())
    {
      popOperator();
    }
  }

  final public void P() throws ParseException {Operator unary;
  ExprAst condExpr, thenExpr, elseExpr;
  List<Def> letBindings;
  ExprAst letExpr;
  ExprAst expr;
  List<ExprAst> exprList = null;
  String name;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LPAREN:{
      jj_consume_token(LPAREN);
      expr = Expr();
      jj_consume_token(RPAREN);
m_operands.push(expr);
      break;
      }
    case INTEGER_LITERAL:{
      jj_consume_token(INTEGER_LITERAL);
m_operands.push(IntLit.create(Integer.parseInt(token.image)));
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
m_operands.push(BoolLit.create(true));
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
m_operands.push(BoolLit.create(false));
      break;
      }
    case IDENTIFIER:{
      jj_consume_token(IDENTIFIER);
name = token.image;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:{
        jj_consume_token(LPAREN);
        exprList = ExprList();
        jj_consume_token(RPAREN);
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
m_operands.push(exprList == null ? VarRef.create(name) : FunCall.create(name, exprList));
      break;
      }
    case PLUS:
    case MINUS:{
      unary = UnaryOperator();
m_operators.push(unary);
      P();
      break;
      }
    case IF:{
      jj_consume_token(IF);
      condExpr = Expr();
      jj_consume_token(THEN);
      thenExpr = Expr();
      jj_consume_token(ELSE);
      elseExpr = Expr();
m_operands.push(IfExpr.create(condExpr, thenExpr, elseExpr));
      break;
      }
    case LET:{
      jj_consume_token(LET);
      letBindings = CompilationUnitAux();
      jj_consume_token(IN);
      letExpr = Expr();
      jj_consume_token(END);
m_operands.push(LetExpr.create(letBindings, letExpr));
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public List<ExprAst> ExprList() throws ParseException {List<ExprAst> exprList = new ExprList();
  ExprAst expr;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TRUE:
    case FALSE:
    case IF:
    case LET:
    case INTEGER_LITERAL:
    case IDENTIFIER:
    case LPAREN:
    case PLUS:
    case MINUS:{
      expr = Expr();
exprList.add(expr);
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[8] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        expr = Expr();
exprList.add(expr);
      }
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      ;
    }
{if ("" != null) return exprList;}
    throw new Error("Missing return statement in function");
  }

  final public Operator BinaryOperator() throws ParseException {Operator oper;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OR:{
      jj_consume_token(OR);
oper = Operator.OR;
      break;
      }
    case AND:{
      jj_consume_token(AND);
oper = Operator.AND;
      break;
      }
    case LT:{
      jj_consume_token(LT);
oper = Operator.LT;
      break;
      }
    case GT:{
      jj_consume_token(GT);
oper = Operator.GT;
      break;
      }
    case LEQ:{
      jj_consume_token(LEQ);
oper = Operator.LEQ;
      break;
      }
    case GEQ:{
      jj_consume_token(GEQ);
oper = Operator.GEQ;
      break;
      }
    case EQ:{
      jj_consume_token(EQ);
oper = Operator.EQ;
      break;
      }
    case NEQ:{
      jj_consume_token(NEQ);
oper = Operator.NEQ;
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
oper = Operator.PLUS;
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
oper = Operator.MINUS;
      break;
      }
    case TIMES:{
      jj_consume_token(TIMES);
oper = Operator.TIMES;
      break;
      }
    case DIVIDE:{
      jj_consume_token(DIVIDE);
oper = Operator.DIVIDE;
      break;
      }
    case REM:{
      jj_consume_token(REM);
oper = Operator.REM;
      break;
      }
    case XOR:{
      jj_consume_token(XOR);
oper = Operator.XOR;
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return oper;}
    throw new Error("Missing return statement in function");
  }

  final public Operator UnaryOperator() throws ParseException {Operator oper;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:{
      jj_consume_token(PLUS);
oper = Operator.UPLUS;
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
oper = Operator.UMINUS;
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return oper;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public EagleMLParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[12];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
      jj_la1_init_2();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x30000,0x30000,0x0,0x20000000,0x600,0x0,0x0,0x20243800,0x0,0x20243800,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x80,0x0,0x0,0x33ff0c00,0x1,0xc00001,0x80,0xc00001,0x33ff0c00,0xc00000,};
   }
   private static void jj_la1_init_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public EagleMLParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public EagleMLParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new EagleMLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public EagleMLParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new EagleMLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public EagleMLParser(EagleMLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(EagleMLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 12; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[65];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 12; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 65; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
