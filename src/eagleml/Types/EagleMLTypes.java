/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.Types;

/**
 *
 * @author Neo
 */
public class EagleMLTypes {
  static public class EagleMLType {}

  static public class IntPrimitiveType extends EagleMLType {
    static public IntPrimitiveType create() {
      return sIntPrimitiveType;
    }

    @Override
    public String toString() {
      return "int";
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

    private static final BoolPrimitiveType sBoolPrimitiveType = new BoolPrimitiveType();
  }
}
