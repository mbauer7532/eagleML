/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleml;

import java.util.List;

/**
 *
 * @author Neo
 */
final public class EagleMLAst {
  static public enum OperAssociativity {
    LEFT,
    RIGHT,
    NOASSOC
  }

  static public enum Arity {
    UNARY,
    BINARY,
    NO_ARITY
  }

  static public enum Operator {
    OR       (0,  OperAssociativity.LEFT,    "||", Arity.BINARY),
    AND      (1,  OperAssociativity.LEFT,    "&&", Arity.BINARY),

    LT       (2,  OperAssociativity.NOASSOC, "<", Arity.BINARY),
    GT       (2,  OperAssociativity.NOASSOC, ">", Arity.BINARY),
    LEQ      (2,  OperAssociativity.NOASSOC, "<=", Arity.BINARY),
    GEQ      (2,  OperAssociativity.NOASSOC, ">=", Arity.BINARY),
    EQ       (2,  OperAssociativity.NOASSOC, "==", Arity.BINARY),
    NEQ      (2,  OperAssociativity.NOASSOC, "!=", Arity.BINARY),

    PLUS     (3,  OperAssociativity.LEFT,    "+", Arity.BINARY),
    MINUS    (3,  OperAssociativity.LEFT,    "-", Arity.BINARY),
    TIMES    (4,  OperAssociativity.LEFT,    "*", Arity.BINARY),
    DIVIDE   (4,  OperAssociativity.LEFT,    "/", Arity.BINARY),
    REM      (4,  OperAssociativity.LEFT,    "%", Arity.BINARY),
    XOR      (5,  OperAssociativity.LEFT,    "^", Arity.BINARY),

    UMINUS   (6,  OperAssociativity.RIGHT,   "u-", Arity.UNARY),
    UPLUS    (6,  OperAssociativity.RIGHT,   "u+", Arity.UNARY),

    NO_OPPER (-1, OperAssociativity.NOASSOC, "NO_OPPER", Arity.NO_ARITY);

    final private int m_precedence;
    final private OperAssociativity m_associativity;
    final String m_name;
    final Arity m_arity;

    private Operator(final int precedence,
                     final OperAssociativity associativity,
                     final String name,
                     final Arity arity) {
      m_precedence = precedence;
      m_associativity = associativity;
      m_name = name;
      m_arity = arity;
    }

    public int getPrecedence() { return m_precedence; }
    public OperAssociativity getAssociativity() { return m_associativity; }
    public boolean isBinary() { return m_arity == Arity.BINARY; }
    public boolean isUnary()  { return m_arity == Arity.UNARY; }
    public boolean isNoOper() { return this == NO_OPPER; }

    @Override
    final public String toString() { return m_name; }

    boolean greaterPrecedence(final Operator op1) {
      if (isBinary() && op1.isBinary())
      {
        final int prec0 = getPrecedence();
        final int prec1 = op1.getPrecedence();

        return prec0 > prec1 || (prec1 == prec0 && getAssociativity() == OperAssociativity.LEFT);
      }
      else if (isUnary() && op1.isBinary())
      {
        return getPrecedence() >= op1.getPrecedence();
      }
      else if (op1.isUnary())
      {
        return false;
      }
      else if (isNoOper())
      {
        return false;
      }
      else {
        throw new RuntimeException("Bad operator comparison.");
      }
    }
  }

  static public class EagleMLType {}

  static public class IntPrimitiveType extends EagleMLType {
    static public IntPrimitiveType create() {
      return sIntPrimitiveType;
    }

    public String toString() {
      return "int";
    }

    static private IntPrimitiveType sIntPrimitiveType = new IntPrimitiveType();
  }

  static public class BoolPrimitiveType extends EagleMLType {
    static public BoolPrimitiveType create() {
      return sBoolPrimitiveType;
    }

    public String toString() {
      return "bool";
    }

    static private BoolPrimitiveType sBoolPrimitiveType = new BoolPrimitiveType();
  }

  final static public class TypedVar {
    static public TypedVar create(final String varName, final EagleMLType varType) {
      return new TypedVar(varName, varType);
    }

    public String toString() {
      return String.format("%s: %s", mVarName, mVarType.toString());
    }

    private TypedVar(final String varName, final EagleMLType varType) {
      mVarName = varName;
      mVarType = varType;
    }

    private final String mVarName;
    private final EagleMLType mVarType;
  }

  static public class ExprAst {}

  final static public class IntLit extends ExprAst {
    public static IntLit create(final int val) {
      return new IntLit(val);
    }

    @Override
    public String toString() {
      return Integer.toString(m_val);
    }

    private IntLit(final int val) {
      m_val = val;
    }

    private final int m_val;
  }

  final static public class BoolLit extends ExprAst {
    public static BoolLit create(boolean b) {
      return b ? sBoolTrue : sBoolFalse;
    }

    private BoolLit(final boolean b) {
      m_val = b;
    }

    @Override
    public String toString() {
      return m_val ? "true" : "else";
    }

    private final boolean m_val;

    private static final BoolLit sBoolTrue  = new BoolLit(true);
    private static final BoolLit sBoolFalse = new BoolLit(false);
  }

  final static public class VarRef extends ExprAst {
    public static VarRef create(final String varName) {
      return new VarRef(varName);
    }

    @Override
    public String toString() {
      return mVarName;
    }

    private VarRef(final String varName) {
      mVarName = varName;
    }

    private final String mVarName;
  }

  final static public class UnaryOper extends ExprAst {
    static public UnaryOper create(final Operator oper, final ExprAst arg0) {
      return new UnaryOper(oper, arg0);
    }

    @Override
    public String toString() {
      return String.format("(%s %s)", mOper.toString(), mArg0.toString());
    }

    private UnaryOper(final Operator oper, final ExprAst arg0) {
      mOper = oper;
      mArg0 = arg0;
    }

    private final Operator mOper;
    private final ExprAst mArg0;
  }

  final static public class BinOper extends ExprAst {
    static public BinOper create(final ExprAst arg0, final Operator oper, final ExprAst arg1) {
      return new BinOper(oper, arg0, arg1);
    }

    @Override
    public String toString() {
      return String.format("(%s %s %s)", mOper.toString(), mArg0.toString(), mArg1.toString());
    }

    private BinOper(final Operator oper, final ExprAst arg0, final ExprAst arg1) {
      mOper = oper;
      mArg0 = arg0;
      mArg1 = arg1;
    }

    private final Operator mOper;
    private final ExprAst mArg0;
    private final ExprAst mArg1;
  }

  final static public class IfExpr extends ExprAst {
    static public IfExpr create(final ExprAst condExpr,
                                final ExprAst thenExpr,
                                final ExprAst elseExpr) {
      return new IfExpr(condExpr, thenExpr, elseExpr);
    }

    @Override
    public String toString() {
      return String.format("(if %s then %s else %s)",
                           mCondExpr.toString(),
                           mThenExpr.toString(),
                           mElseExpr.toString());
    }

    private IfExpr(final ExprAst condExpr,
                   final ExprAst thenExpr,
                   final ExprAst elseExpr) {
      mCondExpr = condExpr;
      mThenExpr = thenExpr;
      mElseExpr = elseExpr;
    }

    private final ExprAst mCondExpr;
    private final ExprAst mThenExpr;
    private final ExprAst mElseExpr;
  }

  final static public class LetExpr extends ExprAst {
    static public LetExpr create(final List<Def> letBindings, final ExprAst letExpr) {
      return new LetExpr(letBindings, letExpr);
    }

    @Override
    public String toString() {
      return String.format("(let %s in %s end)",
                           mLetBindings.toString(),
                           mLetExpr.toString());
    }

    private LetExpr(final List<Def> letBindings, final ExprAst letExpr) {
      mLetBindings = letBindings;
      mLetExpr = letExpr;
    }

    private final List<Def> mLetBindings;
    private final ExprAst mLetExpr;
  }

  static public class Def {}

  final static public class FunDef extends Def
  {
    static public FunDef create(final String funName,
                                final List<TypedVar> tyVars,
                                final EagleMLType funType,
                                final ExprAst expr) {
      return new FunDef(funName, tyVars, funType, expr);
    }

    @Override
    public String toString() {
      return String.format("(def %s%s: %s = %s)",
                           mFunName,
                           mTyVars.toString(),
                           mFunType.toString(),
                           mFunBody.toString());
    }

    private FunDef(final String funName,
                   final List<TypedVar> tyVars,
                   final EagleMLType funType,
                   final ExprAst expr) {
      mFunName = funName;
      mTyVars  = tyVars;
      mFunType = funType;
      mFunBody = expr;
    }

    private final String mFunName;
    private final List<TypedVar> mTyVars;
    private final EagleMLType mFunType;
    private final ExprAst mFunBody;
  }

  final static public class VarDef extends Def {
    static public VarDef create(final String varName,
                                final EagleMLType varType,
                                final ExprAst expr) {
      return new VarDef(varName, varType, expr);
    }

    @Override
    public String toString() {
      final String template = "val %s: %s = %s";
      final String varName = mVarName;
      final String varType = mVarType.toString();
      final String expr = mExpr.toString();

      return String.format(template, varName, varType, expr);
    }

    private VarDef(final String varName,
                   final EagleMLType varType,
                   final ExprAst expr) {
      mVarName = varName;
      mVarType = varType;
      mExpr = expr;
    }

    private final String mVarName;
    private final EagleMLType mVarType;
    private final ExprAst mExpr;
  }
}
