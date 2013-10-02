/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.ast;

/**
 *
 * @author Neo
 */
public class Operators {
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

    public boolean greaterPrecedence(final Operator op1) {
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
}
