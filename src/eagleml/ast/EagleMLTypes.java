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
public class EagleMLTypes {
  static abstract public class EagleMLType implements AstElement {}

  static public class IntPrimitiveType extends EagleMLType {
    static public IntPrimitiveType create() {
      return sIntPrimitiveType;
    }

    @Override
    public String toString() {
      return "int";
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private static final IntPrimitiveType sIntPrimitiveType = new IntPrimitiveType();
  }

  static public class BoolPrimitiveType extends EagleMLType {
    static public BoolPrimitiveType create() {
      return sBoolPrimitiveType;
    }

    @Override
    public String toString() {
      return "bool";
    }

    @Override
    public void accept(final AstVisitor v) {
      v.visit(this);
    }

    private static final BoolPrimitiveType sBoolPrimitiveType = new BoolPrimitiveType();
  }

  static public class FunType extends EagleMLType {
    static public FunType create(final EagleMLType t0, final EagleMLType t1) {
      return new FunType(t0, t1);
    }

    @Override
    public void accept(AstVisitor v) {
      v.visit(this);
    }

    public FunType(final EagleMLType t0, final EagleMLType t1) {
      mt0 = t0;
      mt1 = t1;
    }

    final protected EagleMLType mt0;
    final protected EagleMLType mt1;
  }
}
