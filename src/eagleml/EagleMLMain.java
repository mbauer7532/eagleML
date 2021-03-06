/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eagleml;

import eagleml.ast.EagleMLAst.DefinitionList;
// import eagleml.ast.PrettyPrinter;
import eagleml.ast.PrettyPrinter2;
import eagleml.frontEnd.EagleMLParser;
import eagleml.frontEnd.ParseException;

/**
 *
 * @author Neo
 */
public class EagleMLMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    //performParse(args);
  }

  public static void performParse(String[] args) {
    EagleMLParser parser;
    PrettyPrinter2 pp = new PrettyPrinter2();

    if (args.length == 0) {
      System.out.println("Java Parser Version 1.0.2:  Reading from standard input . . .");
      parser = new EagleMLParser(System.in);
    }
    else if (args.length == 1) {
      System.out.println("Java Parser Version 1.0.2:  Reading from file " + args[0] + " ...");
      try {
        parser = new EagleMLParser(new java.io.FileInputStream(args[0]));
      } catch (java.io.FileNotFoundException e) {
        System.out.println("Java Parser Version 1.0.2:  File " + args[0] + " not found.");
        return;
      }
    }
    else {
      System.out.println("Java Parser Version 1.0.2:  Usage is one of:");
      System.out.println("         java EagleMLParser < inputfile");
      System.out.println("OR");
      System.out.println("         java EagleMLParser inputfile");
      return;
    }

    try {
      parser.initExpressionStacks();
      DefinitionList compUnit = parser.CompilationUnit();
      System.out.println("Java Parser Version 1.0.2:  Java program parsed successfully.");
      //System.out.println(compUnit);
      System.out.print(pp.printAst(compUnit));
    }
    catch (ParseException e) {
      System.out.println("Java Parser Version 1.0.2:  Encountered errors during parse.");
      System.out.println(e);
    }
  }
}
