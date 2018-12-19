package com.ad.server.macros;

import com.ad.server.context.AdContext;
import com.ad.util.constants.AdServerConstants.MACROS;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 */

@Slf4j
@Data
public class ScriptMacros {

  String script = null;
  AdContext adContext = null;

  public ScriptMacros(final String script, final AdContext adContext) {
    this.script = script;
    this.adContext = adContext;

  }

  public String replaceMacros() {
    String macrosGuid = script.replaceAll(MACROS.GUID.getMacro(), adContext.getTag());
    String macrosSession = macrosGuid
        .replaceAll(MACROS.SESSION_ID.getMacro(), adContext.getSessionId());
    log.debug("Script data :: {} ", macrosSession);
    return macrosSession;
  }

}
