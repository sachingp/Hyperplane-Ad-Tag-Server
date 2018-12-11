package com.ad.server.targeting;

import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 */


@Slf4j
public class TagTargeting {

  AdContext adContext = null;

  public TagTargeting(final AdContext adContext) {

    this.adContext = adContext;

  }

  /**
   * @return tag targeting selection.
   */

  public boolean selection() {
    String tag = this.adContext.getTag();
    if (tag != null && CacheService.isTagActive(this.adContext)) {
      Integer creative = CacheService.getCreative(adContext);
      log.info("Creative Id :: {}", creative);
      if (creative != null) {
        adContext.setCreativeId(creative);
        return CacheService.countrySelection(adContext);
      }
    }
    return false;
  }
}
