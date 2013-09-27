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

  static public enum Operator {
    OR      (0, OperAssociativity.LEFT,    "||"),
    AND     (1, OperAssociativity.LEFT,    "&&"),

    LT      (2, OperAssociativity.NOASSOC, "<"),
    GT      (2, OperAssociativity.NOASSOC, ">"),
    LEQ     (2, OperAssociativity.NOASSOC, "<="),
    GEQ     (2, OperAssociativity.NOASSOC, ">="),
    EQ      (2, OperAssociativity.NOASSOC, "=="),
    NEQ     (2, OperAssociativity.NOASSOC, "!="),

    PLUS    (3, OperAssociativity.LEFT,    "+"),
    MINUS   (3, OperAssociativity.LEFT,    "-"),
    TIMES   (4, OperAssociativity.LEFT,    "*"),
    DIVIDE  (4, OperAssociativity.LEFT,    "/"),
    REM     (4, OperAssociativity.LEFT,    "%"),
    XOR     (5, OperAssociativity.LEFT,    "^");

    final private int m_precedence;
    final private OperAssociativity m_associativity;
    final String m_name;

    private Operator(final int precedence, final OperAssociativity associativity, String name) {
      m_precedence = precedence;
      m_associativity = associativity;
      m_name = name;
    }

    public int getPrecedence() { return m_precedence; }
    public OperAssociativity getAssociativity() { return m_associativity; }

    @Override
    final public String toString() { return m_name; }
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
    public static IntLit create(String intStr) {
      return null;
    }
  };

  final static public class BoolLit extends ExprAst {
    public static BoolLit create(boolean b) {
      return null;
    }
  };

  final static public class VarRef extends ExprAst {
    static public VarRef create(String varName) {
      return null;
    }
  };

  final static public class BinOper extends ExprAst {
    static public BinOper create(ExprAst t1, Operator oper, ExprAst t2) {
      return null;
    }
  };

  static public class Def {};

  final static public class FunDef extends Def
  {
    static public FunDef create(String funName, List<TypedVar> tyVars, EagleMLType funType, ExprAst expr) {
      return new FunDef();
    }
  };

  final static public class VarDef extends Def {
    static public VarDef create() {
      return null;
    }
  };
}
