package com.ad.server.targeting;

import com.ad.server.cache.CacheService;
import com.ad.server.cache.CreativeCountryCache;
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
    CreativeCountryCache cache = CacheService.getCountryCacheData();

    Set<String> countryInclusions = cache.getCreativeInclusionsCountryCache().get(creative);

    Set<String> countryExclusions = cache.getCreativeExclusionsCountryCache().get(creative);

    log.info("Countries Cache :: country inclucions :: {}  , country exclusions :: {} ",
        countryInclusions, countryExclusions);

    if (countryInclusions == null || countryInclusions.isEmpty()) {
      if (countryExclusions == null || countryExclusions.isEmpty()) {
        return true;
      } else if (countryExclusions.contains(!Strings
          .isNullOrEmpty(this.adContext.getCountry()))) {
        return false;
      } else {
        return true;
      }
    }

    if (!countryInclusions.isEmpty() && !Strings
        .isNullOrEmpty(this.adContext.getCountry())) {
      if (countryInclusions.contains(this.adContext.getCountry())) {
        return true;
      }
    }
    return false;
  }
}
