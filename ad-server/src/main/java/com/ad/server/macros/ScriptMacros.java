package com.ad.server.macros;

import com.ad.server.context.AdContext;
import com.ad.server.pojo.CreativeAssets;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.constants.AdServerConstants.MACROS;
import com.ad.util.constants.ParamsUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.stringtemplate.v4.ST;

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
    String kv = ParamsUtil.getParamsMapAsString(adContext.getParams());
    ST template = new ST(this.script);
    String width = "0";
    String height = "0";
    template.add(MACROS.PARAMS.getName(), kv);
    if (adContext.getCreativeAssets() != null && !adContext.getCreativeAssets().isEmpty()) {
      // TODO - DOING ONLY 1 now
      CreativeAssets creativeAssets = adContext.getCreativeAssets().get(0);
      if (creativeAssets.getSize() != null) {
        String[] size = creativeAssets.getSize().split(GENERAL.SIZE_DELIMITER);
        width = size[0];
        height = size[1];
      }
      template.add(MACROS.IMAGE_URL_1.getName(), creativeAssets.getAssetUrl());
      template.add(MACROS.WIDTH.getName(), width);
      template.add(MACROS.HEIGHT.getName(), height);
      String data = template.render();
      log.debug("Script data :: {} ", data);
      return data;
    } else {
      return null;
    }
  }

}
