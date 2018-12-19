package com.ad.server.macros;

import com.ad.util.constants.AdServerConstants.CACHE;
import org.apache.commons.collections4.map.PassiveExpiringMap;

/**
 * @author sagupta
 */

public class LocalCache {

  private static LocalCache localCache = null;

  private final PassiveExpiringMap tagMarkupCache;

  private LocalCache() {
    tagMarkupCache = new PassiveExpiringMap(CACHE.TAG_CACHE_TTL);
  }

  public static LocalCache getInstance() {
    if (localCache == null) {
      synchronized (LocalCache.class) {
        if (localCache == null) {
          localCache = new LocalCache();
        }
      }
    }

    return localCache;
  }

  public <T> T get(String key, final Class<T> type) {
    if (tagMarkupCache.containsKey(key)) {
      return (T) this.tagMarkupCache.get(key);
    } else {
      return null;
    }
  }

  public <T> void put(String key, T val) {

    this.tagMarkupCache.put(key, val);
  }
}
