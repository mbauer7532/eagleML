/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml;

/**
 *
 * @author Neo
 */
public class TestUtils {
  @FunctionalInterface
  public interface Function {
    public void eval();
}
  private static void nIter(final int n, final Function f) {
    for (int i = 0; i != n; ++i) {
      f.eval();
    }

    return;
  }

  public static void printTestHeader(final String lab) {
    final int siz = lab.length() + 1;

    System.out.println();
    System.out.print(lab);
    System.out.println(":");
    nIter(siz, () -> System.out.print("="));
    System.out.println();

    return;
  }
}
