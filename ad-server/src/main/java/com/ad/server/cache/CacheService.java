package com.ad.server.cache;


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
   * @return list of countries targeted to tag.
   */
  public static Set<String> getCountryCacheData(final String country) {

    Set<String> data = TagCache.getInstance().countryCache.get(country);

    return data;

  }

  /**
   * @return all active Guid Set.
   */

  public static Set<String> getAllActiveTagGuids() {
    return TagCache.getInstance().allActiveTagGuidCache.get(CACHE.ALL_ACTIVE_TAG_GUID_CACHE_KEY);
  }

  /**
   * @return if the tag guid is active or disabled.
   */

  public static boolean isTagActive(final String tagGuid) {

    return TagCache.getInstance().allActiveTagGuidCache != null && !TagCache
        .getInstance().allActiveTagGuidCache.isEmpty()
        && TagCache.getInstance().allActiveTagGuidCache.get(tagGuid) != null ? true : false;

  }

}
