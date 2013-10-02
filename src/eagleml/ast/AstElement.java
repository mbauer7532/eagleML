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
public interface AstElement {
  public void accept(final AstVisitor v);
}
