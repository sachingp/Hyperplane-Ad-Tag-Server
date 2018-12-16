package com.ad.server.targeting;

import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.server.pojo.Creative;
import com.ad.server.pojo.CreativeTag;
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
      CreativeTag creativeTag = CacheService.getCreative(adContext);
      log.debug("Creative Tag :: {}", creativeTag);
      if (creativeTag != null) {
        adContext.setCreativeId(creativeTag.getCreativeId());
        Creative creative = CacheService.getCreative(creativeTag.getCreativeId());
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
