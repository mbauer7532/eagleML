/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleml.ast;

import eagleml.ast.Operators.Operator;
import eagleml.ast.EagleMLTypes.EagleMLType;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Neo
 */
final public class EagleMLAst {
  final static public class DefinitionList extends ArrayList<Def>
                                           implements AstElement {
    @Override
    final public String toString() {
      return String.format("(%s)",
                           stream()
                           .map(d -> String.valueOf(d))
                           .collect(Collectors.joining("\n")));
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }
  }

  final static public class TypedVar implements AstElement {
    static public TypedVar create(final String varName,
                                  final EagleMLType varType) {
      return new TypedVar(varName, varType);
    }

    @Override
    public String toString() {
      return String.format("%s: %s", mVarName, mVarType.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private TypedVar(final String varName, final EagleMLType varType) {
      mVarName = varName;
      mVarType = varType;
    }

    protected final String mVarName;
    protected final EagleMLType mVarType;
  }

  final static public class TypedVarList extends ArrayList<TypedVar>
                                         implements AstElement {
    @Override
    final public String toString() {
      return String.format("(%s)",
                           stream()
                           .map(d -> String.valueOf(d))
                           .collect(Collectors.joining(", ")));
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }
}

  static abstract public class ExprAst implements AstElement {
    protected EagleMLType mExprType; // Populated by the typechecker later.
  }

  final static public class ExprList extends ArrayList<ExprAst>
                                     implements AstElement {
    @Override
    final public String toString() {
      return String.format("(%s)",
                           stream()
                           .map(d -> String.valueOf(d))
                           .collect(Collectors.joining(", ")));
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }
  }

  final static public class IntLit extends ExprAst {
    public static IntLit create(final int val) {
      return new IntLit(val);
    }

    @Override
    public String toString() {
      return Integer.toString(m_val);
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private IntLit(final int val) {
      m_val = val;
    }

    protected final int m_val;
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
      return m_val ? "true" : "false";
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    protected final boolean m_val;

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

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private VarRef(final String varName) {
      mVarName = varName;
    }

    protected final String mVarName;
  }

  final static public class UnaryOper extends ExprAst {
    static public UnaryOper create(final Operator oper, final ExprAst arg0) {
      return new UnaryOper(oper, arg0);
    }

    @Override
    public String toString() {
      return String.format("(%s %s)", mOper.toString(), mArg0.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private UnaryOper(final Operator oper, final ExprAst arg0) {
      mOper = oper;
      mArg0 = arg0;
    }

    protected final Operator mOper;
    protected final ExprAst mArg0;
  }

  final static public class BinOper extends ExprAst {
    static public BinOper create(final ExprAst arg0,
                                 final Operator oper,
                                 final ExprAst arg1) {
      return new BinOper(oper, arg0, arg1);
    }

    @Override
    public String toString() {
      return String.format("(%s %s %s)",
                           mOper.toString(),
                           mArg0.toString(),
                           mArg1.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private BinOper(final Operator oper,
                    final ExprAst arg0,
                    final ExprAst arg1) {
      mOper = oper;
      mArg0 = arg0;
      mArg1 = arg1;
    }

    protected final Operator mOper;
    protected final ExprAst mArg0;
    protected final ExprAst mArg1;
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

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private IfExpr(final ExprAst condExpr,
                   final ExprAst thenExpr,
                   final ExprAst elseExpr) {
      mCondExpr = condExpr;
      mThenExpr = thenExpr;
      mElseExpr = elseExpr;
    }

    protected final ExprAst mCondExpr;
    protected final ExprAst mThenExpr;
    protected final ExprAst mElseExpr;
  }

  final static public class LetExpr extends ExprAst {
    static public LetExpr create(final DefinitionList letBindings,
                                 final ExprAst letExpr) {
      return new LetExpr(letBindings, letExpr);
    }

    @Override
    public String toString() {
      return String.format("(let %s in %s end)",
                           mLetBindings.toString(),
                           mLetExpr.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private LetExpr(final DefinitionList letBindings,
                    final ExprAst letExpr) {
      mLetBindings = letBindings;
      mLetExpr = letExpr;
    }

    protected final DefinitionList mLetBindings;
    protected final ExprAst mLetExpr;
  }

  final static public class FunCall extends ExprAst {
    static public FunCall create(final String funName,
                                 final ExprList exprList) {
      return new FunCall(funName, exprList);
    }

    @Override
    public String toString() {
      return String.format("(%s %s)", mFunName, mExprList.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private FunCall(final String funName, final ExprList exprList) {
      mFunName = funName;
      mExprList = exprList;
    }

    protected final String mFunName;
    protected final ExprList mExprList;
  }

  static abstract public class Def implements AstElement {}

  final static public class FunDef extends Def
  {
    static public FunDef create(final String funName,
                                final TypedVarList typedVars,
                                final EagleMLType funType,
                                final ExprAst expr) {
      return new FunDef(funName, typedVars, funType, expr);
    }

    @Override
    public String toString() {
      return String.format("(def %s%s: %s = %s)",
                           mFunName,
                           mTypedVars.toString(),
                           mFunType.toString(),
                           mFunBody.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private FunDef(final String funName,
                   final TypedVarList typedVars,
                   final EagleMLType funType,
                   final ExprAst expr) {
      mFunName   = funName;
      mTypedVars = typedVars;
      mFunType   = funType;
      mFunBody   = expr;
    }

    protected final String mFunName;
    protected final TypedVarList mTypedVars;
    protected final EagleMLType mFunType;
    protected final ExprAst mFunBody;
  }

  final static public class VarDef extends Def {
    static public VarDef create(final String varName,
                                final EagleMLType varType,
                                final ExprAst expr) {
      return new VarDef(varName, varType, expr);
    }

    @Override
    public String toString() {
      return String.format("val %s: %s = %s",
                           mVarName,
                           mVarType.toString(),
                           mExpr.toString());
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private VarDef(final String varName,
                   final EagleMLType varType,
                   final ExprAst expr) {
      mVarName = varName;
      mVarType = varType;
      mExpr = expr;
    }

    protected final String mVarName;
    protected final EagleMLType mVarType;
    protected final ExprAst mExpr;
  }
}
