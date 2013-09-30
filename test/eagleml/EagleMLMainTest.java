/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Neo
 */
public class EagleMLMainTest {

  public EagleMLMainTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of main method, of class EagleMLMain.
   */
  @Test
  public void testPerformParse1() {
    System.out.println("\nperformParse1:");
    System.out.println("==============");
    String[] args = {"test1.eagleML" };
    EagleMLMain.performParse(args);
  }

  @Test
  public void testPerformParse2() {
    System.out.println("\nperformParse2:");
    System.out.println("==============");

    String[] args = {"test2.eagleML" };
    EagleMLMain.performParse(args);
  }

}
