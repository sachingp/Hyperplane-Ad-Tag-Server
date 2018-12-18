package com.ad.server.macros;

import com.ad.util.constants.AdServerConstants.CACHE;
import org.apache.commons.collections4.map.PassiveExpiringMap;

/**
 * @author sagupta
 */

public class TagTTLCache {

  private static TagTTLCache tagTTLCache = null;

  private final PassiveExpiringMap tagMarkupCache;

  private TagTTLCache() {
    tagMarkupCache = new PassiveExpiringMap(CACHE.TAG_CACHE_TTL);
  }

  public static TagTTLCache getInstance() {
    if (tagTTLCache == null) {
      synchronized (TagTTLCache.class) {
        if (tagTTLCache == null) {
          tagTTLCache = new TagTTLCache();
        }
      }
    }

    return tagTTLCache;
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
