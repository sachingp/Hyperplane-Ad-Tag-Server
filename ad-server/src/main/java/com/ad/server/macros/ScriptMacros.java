package com.ad.server.macros;

import com.ad.server.context.AdContext;

/**
 * @author sagupta
 */

public class ScriptMacros {

  String script = null;
  AdContext adContext = null;

  public ScriptMacros(final String script, final AdContext adContext) {
    this.script = script;
    this.adContext = adContext;

  }

  public String addMacros() {
    //TODO
    return null;
  }

}
