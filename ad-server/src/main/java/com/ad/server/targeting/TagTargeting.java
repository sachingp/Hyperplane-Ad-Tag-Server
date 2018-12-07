package com.ad.server.targeting;

import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

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
    if (tag != null && CacheService.isTagActive(tag)) {
      Integer creative = CacheService.getCreative(tag);
      log.info("Creative Id :: {}", creative);
      if (creative != null) {
        adContext.setCreativeId(creative);
        return countrySelection(creative);
      }
    }
    return false;
  }

  /**
   * @return country selection.
   */

  private boolean countrySelection(Integer creative) {
    Set<String> creativeCountries = CacheService.getCountryCacheData(creative);

    log.info("Countries :: {}", creativeCountries);

    if (creativeCountries == null || creativeCountries.isEmpty()) {
      return true;
    }

    if (!creativeCountries.isEmpty() && !Strings.isNullOrEmpty(this.adContext.getCountry())) {
      if (creativeCountries.contains(this.adContext.getCountry())) {
        return true;
      }
    }
    return false;
  }
}
