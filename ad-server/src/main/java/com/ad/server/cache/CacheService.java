package com.ad.server.cache;


import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;

import java.util.Set;

/**
 * @author sagupta
 */
public class CacheService {

  public CacheService() {
    throw new AssertionError("Error while initializing the class");
  }

  /**
   * @return list of countries targeted to creative.
   */
  public static Set<String> getCountryCacheData(final Integer creative) {

    Set<String> data = TagCache.getInstance().creativeCountryCache.get(creative);

    return data;

  }

  /**
   * @return all active tags.
   */

  public static Set<String> getAllActiveTags() {
    return TagCache.getInstance().allActiveTagCache.get(CACHE.ALL_ACTIVE_TAG_GUID_CACHE_KEY);
  }

  /**
   * @return if the tag is active or disabled.
   */

  public static boolean isTagActive(final String tagGuid) {

    return TagCache.getInstance().allActiveTagCache != null && !TagCache
        .getInstance().allActiveTagCache.isEmpty()
        && TagCache.getInstance().allActiveTagCache.get(tagGuid) != null ? true : false;

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
    return TagCache.getInstance().tagCreativeMapCache.get(tag);
  }

}
