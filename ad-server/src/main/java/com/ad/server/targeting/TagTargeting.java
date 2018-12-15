package com.ad.server.targeting;

import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.server.pojo.Creative;
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
    log.debug("Tag Request Targeting :: {}", tag);
    if (tag != null && CacheService.isTagActive(this.adContext)) {
      Integer creativeId = CacheService.getCreative(adContext);
      log.debug("Creative Id :: {}", creativeId);
      if (creativeId != null) {
        adContext.setCreativeId(creativeId);
        Creative creative = CacheService.getCreative(creativeId);
        if (creative != null) {
          adContext.setCampaignId(creative.getCampaignId());
          adContext.setAdvertiserId(creative.getAdvertiserId());
          adContext.setAccountId(creative.getAccountId());
          return CacheService.countrySelection(adContext);
        }

      }
    }
    return false;
  }
}
