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
    if (CacheService.setTagDetails(this.adContext)) {
      return CacheService.countrySelection(adContext);
    }

    return false;
  }
}
