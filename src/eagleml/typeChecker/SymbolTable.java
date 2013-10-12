/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eagleml.typeChecker;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Neo
 */
public class SymbolTable {
  public class Symbol {
    private String mSymName;
  }

  static public SymbolTable create() {
    return new SymbolTable();
  }

  public void addSymbol(final Symbol sym) {
    mMap.put(sym.mSymName, sym);
  }

  public Symbol getSymbol(final String var) {
    final Symbol sym = mMap.get(var);
    assert sym != null;

    return sym;
  }

  private SymbolTable() {
    mMap = new TreeMap<>();
  }

  private final Map<String, Symbol> mMap;
}
