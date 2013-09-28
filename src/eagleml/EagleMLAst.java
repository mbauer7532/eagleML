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
  };

  static public enum Arity {
    UNARY,
    BINARY,
    NO_ARITY
  };

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

    private Operator(final int precedence, final OperAssociativity associativity, String name, Arity arity) {
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
  };

  static public class EagleMLType {}

  final static public class TypedVar {
    static public TypedVar create(String name, EagleMLType typ) {
      return new TypedVar();
    }
  }

  static public EagleMLType makeIntType() { return new EagleMLType(); }
  static public EagleMLType makeBoolType() { return new EagleMLType(); }

  static public class ExprAst {};

  final static public class IntLit extends ExprAst {
    public static IntLit create(final int val) {
      return new IntLit(val);
    }

    private IntLit(final int val) {
      m_val = val;
    }

    private final int m_val;
  };

  final static public class BoolLit extends ExprAst {
    public static BoolLit create(boolean b) {
      return b ? sBoolTrue : sBoolFalse;
    }

    private BoolLit(final boolean b) {
      m_val = b;
    }

    private final boolean m_val;

    private static final BoolLit sBoolTrue  = new BoolLit(true);
    private static final BoolLit sBoolFalse = new BoolLit(false);
  }

  final static public class VarRef extends ExprAst {
    public static VarRef create(final String varName) {
      return new VarRef(varName);
    }

    private VarRef(final String varName) {
      m_varName = varName;
    }

    private final String m_varName;
  }

  final static public class UnaryOper extends ExprAst {
    static public UnaryOper create(final Operator oper, final ExprAst arg0) {
      return new UnaryOper(oper, arg0);
    }

    private UnaryOper(final Operator oper, final ExprAst arg0) {
      mOper = oper;
      mArg0 = arg0;
    }

    private final Operator mOper;
    private final ExprAst mArg0;
  };

  final static public class BinOper extends ExprAst {
    static public BinOper create(final ExprAst arg0, final Operator oper, final ExprAst arg1) {
      return new BinOper(oper, arg0, arg1);
    }

    private BinOper(final Operator oper, final ExprAst arg0, final ExprAst arg1) {
      mOper = oper;
      mArg0 = arg0;
      mArg1 = arg1;
    }

    private final Operator mOper;
    private final ExprAst mArg0;
    private final ExprAst mArg1;
  };

  static public class Def {};

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
      final String template = "(def %s%s = %s)";
      final String funName = mFunName;
      final String args = mTyVars.toString();
      final String body = mFunBody.toString();

      return String.format(template, funName, args, body);
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
  };

  final static public class VarDef extends Def {
    static public VarDef create() {
      return null;
    }
  };
}
