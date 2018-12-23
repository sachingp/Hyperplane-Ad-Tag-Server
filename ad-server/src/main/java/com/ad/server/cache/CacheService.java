package com.ad.server.cache;


import static com.ad.util.ObjectUtil.getBytes;
import static com.ad.util.ObjectUtil.readObject;

import com.ad.server.context.AdContext;
import com.ad.server.macros.LocalCache;
import com.ad.server.pojo.Creative;
import com.ad.server.pojo.CreativeAssets;
import com.ad.server.pojo.CreativeTag;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.exception.AdServicesSerializationException;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author sagupta
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class CacheService {

  public CacheService() {
    throw new AssertionError("Error while initializing the class");
  }

  /**
   * @return if the tag is active or disabled.
   */

  public static boolean isTagActive(final AdContext adContext) {

    return AdServerCache.getInstance().tagCreativeCache.evaluate(adContext);

  }

  /**
   * @return get script data for the tag.
   */

  public static String getTagScriptData(String tag) {
    log.debug("Request for Ad Tag :: {} ", tag);
    String tagData = LocalCache.getInstance().get(tag, String.class);
    try {
      if (tagData == null) {
        log.debug("Log Data not in Local Cache :: {}", tagData);
        final byte[] tagByteData = AdServerRedisClient.getInstance().get(getBytes(tag));
        if (tagByteData != null) {
          tagData = readObject(String.class, tagByteData);
          LocalCache.getInstance().put(tag, tagData);
        }
      }
    } catch (AdServicesSerializationException e) {
      log.error(e.getMessage(), e);
    }

    return tagData;
  }


  /**
   * @return get script data for the tag.
   */

  public static String getClickThroughURL(String sessionId, AdContext adContext) {
    log.debug("Request for Ad Click :: {} ", sessionId);
    String tagData = LocalCache.getInstance().get(sessionId, String.class);
    if (tagData == null) {
      tagData = AdServerRedisClient.getInstance().get(sessionId);
      if (tagData == null) {
        // TODO for ASSET ID
        tagData = adContext.getCreativeAssets().get(0).getClickUrl();
      }
      if (tagData != null) {
        LocalCache.getInstance().put(sessionId, tagData);
      }

    }
    return tagData;
  }


  /**
   * @return creative Id for the tag.
   */

  public static CreativeTag getCreative(AdContext adContext) {

    Map<String, CreativeTag> tagCreativeCache = AdServerCache.getInstance().tagCreativeCache
        .getCache(Map.class);
    return tagCreativeCache != null ? tagCreativeCache.get(adContext.getTag()) : null;
  }

  /**
   * @return country selection.
   */

  public static boolean countrySelection(AdContext adContext) {

    return AdServerCache.getInstance().creativeCountryCache.evaluate(adContext);
  }

  public static Creative getCreative(int creativeId) {
    Map<Integer, Creative> cache = AdServerCache.getInstance().creativeDetailsCache
        .getCache(Map.class);
    return cache != null ? cache.get(creativeId) : null;

  }

  public static List<CreativeAssets> getCreativeAssets(int creativeId) {
    Map<Integer, List<CreativeAssets>> cache = AdServerCache.getInstance().creativeAssetsCache
        .getCache(Map.class);
    return cache != null ? cache.get(creativeId) : null;

  }

  public static boolean setTagDetails(AdContext adContext) {
    String tag = adContext.getTag();
    log.debug("Tag Request Targeting :: {}", tag);
    if (tag != null && CacheService.isTagActive(adContext)) {
      CreativeTag creativeTag = CacheService.getCreative(adContext);
      log.debug("Creative Tag :: {}", creativeTag);
      if (creativeTag != null) {
        adContext.setCreativeId(creativeTag.getCreativeId());
        Creative creative = CacheService.getCreative(creativeTag.getCreativeId());
        if (creative != null) {
          List<CreativeAssets> creativeAssets = CacheService
              .getCreativeAssets(creative.getCreativeId());
          adContext.setCreativeAssets(creativeAssets);
          adContext.setCampaignId(creative.getCampaignId());
          adContext.setAdvertiserId(creative.getAdvertiserId());
          adContext.setAccountId(creative.getAccountId());
          return true;
        }
      }

    }
    return false;
  }

}
