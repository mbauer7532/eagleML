/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.ast;

import eagleml.ast.EagleMLAst.*;
import eagleml.ast.EagleMLTypes.*;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 *
 * @author Neo
 */

public class PrettyPrinter implements AstVisitor {
  public String printAst(final DefinitionList compUnit) {
    compUnit.accept(this);
    return String.format("%s\n", mDefStrings.stream().collect(Collectors.joining("\n")));
  }

  @Override
  public void visit(final DefinitionList defList) {
    defList.forEach(def -> { def.accept(this); mDefStrings.add(mRes); });
  }

  @Override
  public void visit(final FunDef funDef) {
    funDef.mTypedVars.accept(this);
    final String tyVarsStr = mRes;

    funDef.mFunType.accept(this);
    final String funTypeStr = mRes;

    funDef.mFunBody.accept(this);
    final String funBodyStr = mRes;

    mRes = String.format("(def %s%s: %s = %s)",
                         funDef.mFunName,
                         tyVarsStr,
                         funTypeStr,
                         funBodyStr);
  }

  @Override
  public void visit(final VarDef varDef) {
    varDef.mVarType.accept(this);
    final String varTypeStr = mRes;
    varDef.mExpr.accept(this);
    final String exprStr = mRes;

    mRes = String.format("val %s: %s = %s", varDef.mVarName, varTypeStr, exprStr);
  }

  @Override
  public void visit(TypedVar typedVar) {
    typedVar.mVarType.accept(this);
    final String typeStr = mRes;
    mRes = String.format("%s: %s", typedVar.mVarName, typeStr);
  }

  @Override
  public void visit(final TypedVarList typedVarList) {
    mRes = String.format("(%s)", typedVarList.stream().map(d -> { d.accept(this); return mRes; }).collect(Collectors.joining(", ")));
  }

  @Override
  public void visit(final IntLit intLit) {
    mRes = Integer.toString(intLit.m_val);
  }

  @Override
  public void visit(final BoolLit boolLit) {
    mRes = boolLit.m_val ? "true" : "false";
  }

  @Override
  public void visit(final VarRef varRef) {
    mRes = varRef.mVarName;
  }

  @Override
  public void visit(final UnaryOper uOper) {
    uOper.mArg0.accept(this);
    final String arg0Str = mRes;
    mRes = String.format("(%s %s)", uOper.mOper.toString(), arg0Str);
  }

  @Override
  public void visit(final BinOper binOper) {
    mRes = String.format("(%s %s %s)",
                         binOper.mOper.toString(),
                         evalExpr(binOper.mArg0),
                         evalExpr(binOper.mArg1));
  }

  @Override
  public void visit(final IfExpr ifExpr) {
    ifExpr.mCondExpr.accept(this);
    final String condExprStr = mRes;

    ifExpr.mThenExpr.accept(this);
    final String thenExprStr = mRes;

    ifExpr.mElseExpr.accept(this);
    final String elseExprStr = mRes;

    mRes = String.format("(if %s then %s else %s)",
                         condExprStr,
                         thenExprStr,
                         elseExprStr);
  }

  @Override
  public void visit(final LetExpr letExpr) {
    letExpr.mLetBindings.accept(this);
    final String letBindingsStr = mRes;
    letExpr.mLetExpr.accept(this);
    final String letExprStr = mRes;

    mRes = String.format("(let %s in %s end)", letBindingsStr, letExprStr);
  }

  @Override
  public void visit(final FunCall funCall) {
    funCall.mExprList.accept(this);
    mRes = String.format("(%s %s)", funCall.mFunName, mRes);
  }

  @Override
  public void visit(final ExprList exprList) {
    mRes = String.format("(%s)", exprList.stream().map(d -> { d.accept(this); return mRes; }).collect(Collectors.joining(", ")));
  }

  @Override
  public void visit(final IntPrimitiveType intTyp) {
    mRes = "int";
  }

  @Override
  public void visit(final BoolPrimitiveType boolTyp) {
    mRes = "bool";
  }

  @Override
  public void visit(final FunType funType) {
    funType.mt0.accept(this);
    final String t0Str = mRes;

    funType.mt1.accept(this);
    final String t1Str = mRes;

    mRes = String.format("(%s -> %s)", t0Str, t1Str);
  }

  public PrettyPrinter()
  {
    mRes = "";
    mDefStrings = new ArrayList<>();
  }

  private String evalExpr(final ExprAst expr) {
    expr.accept(this);
    return mRes;
  }

  private String mRes;
  private final ArrayList<String> mDefStrings;
}
