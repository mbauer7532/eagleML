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
public class EagleMLTest {

  public EagleMLTest() {
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
   * Test of main method, of class EagleML.
   */
  @Test
  public void testPerformParse() {
    System.out.println("performParse");
    String[] args = {"test1.eagleML" };
    EagleML.performParse(args);
  }
}
