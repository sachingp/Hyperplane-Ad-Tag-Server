package com.ad.server.cache;


import com.ad.util.client.AdServerRedisClient;
import lombok.extern.slf4j.Slf4j;

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
   * @return list of countries targeted to creative.
   */
  public static CreativeCountryCache getCountryCacheData() {

    return AdServerCache.getInstance().creativeCountryCache;
  }

  /**
   * @return all active tags.
   */

  public static Cache getAllActiveTags() {
    return AdServerCache.getInstance().allActiveTagCache;
  }

  /**
   * @return if the tag is active or disabled.
   */

  public static boolean isTagActive(final String tagGuid) {

    return AdServerCache.getInstance().allActiveTagCache != null && !AdServerCache
        .getInstance().allActiveTagCache.getAllActiveTagCache().isEmpty()
        && AdServerCache.getInstance().allActiveTagCache.allActiveTagCache.get(tagGuid) != null
        ? true : false;

  }

  /**
   * @return get script data for the tag.
   */

  public static String getTagScriptData(String tag) {
    return AdServerRedisClient.getInstance().get(tag);
  }

  /**
   * @return creative Id for the tag.
   */

  public static Integer getCreative(String tag) {
    return AdServerCache.getInstance().tagCreativeMapCache.getTagCreativeMapCache().get(tag);
  }
}
