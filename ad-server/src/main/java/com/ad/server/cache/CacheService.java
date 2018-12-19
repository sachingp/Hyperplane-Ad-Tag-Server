package com.ad.server.cache;


import com.ad.server.context.AdContext;
import com.ad.server.macros.LocalCache;
import com.ad.server.pojo.Creative;
import com.ad.server.pojo.CreativeTag;
import com.ad.util.client.AdServerRedisClient;
import lombok.extern.slf4j.Slf4j;

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
    if (tagData == null) {

      tagData = AdServerRedisClient.getInstance().get(tag);
      if (tagData != null) {
        LocalCache.getInstance().put(tag, tagData);
      }

    }
    return tagData;
  }


  /**
   * @return get script data for the tag.
   */

  public static String getClickThroughURL(String sessionId) {
    log.debug("Request for Ad Click :: {} ", sessionId);
    String tagData = LocalCache.getInstance().get(sessionId, String.class);
    if (tagData == null) {

      tagData = AdServerRedisClient.getInstance().get(sessionId);
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

}
