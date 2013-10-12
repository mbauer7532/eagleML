/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.typeChecker;

import eagleml.ast.AstVisitor;
import eagleml.ast.EagleMLAst.*;
import eagleml.ast.EagleMLTypes;

/**
 *
 * @author Neo
 */
public class TypeChecker implements AstVisitor {

  private final SymbolTable mSymTab;

  public TypeChecker(final SymbolTable symTab) {
    mSymTab = symTab;
  }

  public void tc(final DefinitionList defList) {
    visit(defList);
  }

  @Override
  public void visit(final DefinitionList defList) {
    defList.forEach(d -> d.accept(this));
  }

  @Override
  public void visit(final FunDef funDef) {
    final String funName = funDef.getFunName();

  }

  @Override
  public void visit(final VarDef varDef) {

  }

  @Override
  public void visit(final TypedVarList typedVarList) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final TypedVar typedVar) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final ExprList exprList) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final IntLit intLit) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final BoolLit boolLit) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final VarRef varRef) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final UnaryOper uOper) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final BinOper binOper) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final IfExpr binOper) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final LetExpr binOper) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final FunCall binOper) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final EagleMLTypes.IntPrimitiveType intTyp) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final EagleMLTypes.BoolPrimitiveType boolTyp) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void visit(final EagleMLTypes.FunType boolTyp) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
