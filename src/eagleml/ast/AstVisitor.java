/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.ast;

import eagleml.ast.EagleMLAst.*;
import eagleml.ast.EagleMLTypes.*;

/**
 *
 * @author Neo
 */
public interface AstVisitor {
  public void visit(final DefinitionList defList);
  public void visit(final FunDef funDef);
  public void visit(final VarDef varDef);

  public void visit(final TypedVarList typedVarList);
  public void visit(final TypedVar typedVar);
  public void visit(final ExprList exprList);

  public void visit(final IntLit intLit);
  public void visit(final BoolLit boolLit);
  public void visit(final VarRef varRef);

  public void visit(final UnaryOper uOper);
  public void visit(final BinOper binOper);

  public void visit(final IfExpr binOper);
  public void visit(final LetExpr binOper);
  public void visit(final FunCall binOper);

  public void visit(final IntPrimitiveType intTyp);
  public void visit(final BoolPrimitiveType boolTyp);
}
