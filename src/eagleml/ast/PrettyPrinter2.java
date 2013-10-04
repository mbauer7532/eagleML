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

public class PrettyPrinter2 implements AstVisitor {
  public String printAst(final DefinitionList compUnit) {
    // The only reason the next line is here and we don't simply do
    // compUnit.accept(this) is because we want the newline at the top
    // level but not inside a let expression.
    compUnit.forEach(def -> { def.accept(this); mb.append("\n"); });
    return mb.toString();
  }

  @Override
  public void visit(final DefinitionList defList) {
    defList.forEach(def -> { def.accept(this); });
  }

  @Override
  public void visit(final FunDef funDef) {
    mb.append("(def ");
    mb.append(funDef.mFunName);
    funDef.mTypedVars.accept(this);
    mb.append("(: ");
    funDef.mFunType.accept(this);
    mb.append(" = ");
    funDef.mFunBody.accept(this);
    mb.append(")");
    }

  @Override
  public void visit(final VarDef varDef) {
    mb.append("(val ");
    mb.append(varDef.mVarName);
    mb.append(": ");
    varDef.mVarType.accept(this);
    mb.append(" = ");
    varDef.mExpr.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final TypedVar typedVar) {
    mb.append(typedVar.mVarName);
    mb.append(": ");
    typedVar.mVarType.accept(this);
  }

  @Override
  public void visit(final TypedVarList typedVarList) {
    mb.append("(");
    final int count = typedVarList.size();
    if (count > 0) {
      int i = 0;
      for (; i != count - 1; ++i) {
        final TypedVar typedVar = typedVarList.get(i);
        typedVar.accept(this);
        mb.append(" ");
      }
      typedVarList.get(i).accept(this);
    }
    mb.append(")");
  }

  @Override
  public void visit(final IntLit intLit) {
    mb.append(Integer.toString(intLit.m_val));
  }

  @Override
  public void visit(final BoolLit boolLit) {
    mb.append(boolLit.m_val ? "true" : "false");
  }

  @Override
  public void visit(final VarRef varRef) {
    mb.append(varRef.mVarName);
  }

  @Override
  public void visit(final UnaryOper uOper) {
    mb.append("(");
    mb.append(uOper.mOper.toString());
    uOper.mArg0.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final BinOper binOper) {
    mb.append("(");
    mb.append(binOper.mOper.toString());
    mb.append(" ");
    binOper.mArg0.accept(this);
    mb.append(" ");
    binOper.mArg1.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final IfExpr ifExpr) {
    mb.append("(if ");

    ifExpr.mCondExpr.accept(this);
    mb.append(" ");
    ifExpr.mThenExpr.accept(this);
    mb.append(" ");
    ifExpr.mElseExpr.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final LetExpr letExpr) {
    mb.append("(let ");
    letExpr.mLetBindings.accept(this);
    letExpr.mLetExpr.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final FunCall funCall) {
    mb.append("(");
    mb.append(funCall.mFunName);
    mb.append(" ");
    funCall.mExprList.accept(this);
    mb.append(")");
  }

  @Override
  public void visit(final ExprList exprList) {
    final int count = exprList.size();
    if (count > 0) {
      int i = 0;
      for (; i != count - 1; ++i) {
        final ExprAst expr = exprList.get(i);
        expr.accept(this);
        mb.append(" ");
      }
      exprList.get(i).accept(this);
    }
  }

  @Override
  public void visit(final IntPrimitiveType intTyp) {
    mb.append("int");
  }

  @Override
  public void visit(final BoolPrimitiveType boolTyp) {
    mb.append("bool");
  }

  public PrettyPrinter2()
  {
    mb = new StringBuilder();
  }

  private StringBuilder mb;
}
