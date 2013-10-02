/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.ast;

import eagleml.ast.EagleMLAst.*;

/**
 *
 * @author Neo
 */

public class PrettyPrinter implements AstVisitor {
  @Override
  public void visit(DefinitionList defList) {
    defList.forEach(def -> def.accept(this));
  }

  @Override
  public void visit(FunDef funDef) {
    mResBuilder.append("(def ");
    mResBuilder.append(funDef.mFunName);
    funDef.mTyVars.accept(this);
    mResBuilder.append(": ");
    funDef.mFunType.accept(this);
    mResBuilder.append(" =\n");
    //mIntentationLevel += sIndentationDepth;
    funDef.mFunBody.accept(this);
    mResBuilder.append(")\n");
    //mIntentationLevel -= sIndentationDepth;
  }

  @Override
  public void visit(VarDef varDef) {
    mResBuilder.append(String.format("val %s:", varDef.mVarName));
    varDef.mVarType.accept(this);
    mResBuilder.append(" = ");
    varDef.mExpr.accept(this);
    mResBuilder.append("\n");
  }

  @Override
  public void visit(TypedVarList typedVarList) {
    mResBuilder.append("(");

    final int siz = typedVarList.size();
    for (int i = 0; i != siz; ++i) {
      final TypedVar tv = typedVarList.get(i);
      tv.accept(this);
      if (i != siz - 1) {
        mResBuilder.append(", ");
      }
    }
            forEach(t -> { t.accept(this); mResBuilder.append(", "));
  }

  @Override
  public void visit(IntLit intLit) {

  }

  @Override
  public void visit(BoolLit boolLit) {

  }

  @Override
  public void visit(VarRef varRef) {

  }

  @Override
  public void visit(UnaryOper uOper) {

  }

  @Override
  public void visit(BinOper binOper) {

  }

  @Override
  public void visit(IfExpr binOper) {

  }

  @Override
  public void visit(LetExpr binOper) {

  }

  @Override
  public void visit(FunCall binOper) {

  }

  private PrettyPrinter()
  {
    mIntentationLevel = 0;
    mResBuilder = new StringBuilder();
  }

  @Override
  public void visit(ExprList exprList) {

  }

  @Override
  public void visit(EagleMLTypes.IntPrimitiveType intTyp) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(EagleMLTypes.BoolPrimitiveType boolTyp) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  private int mIntentationLevel;
  private final StringBuilder mResBuilder;
  private static final int sIndentationDepth = 4;
}
