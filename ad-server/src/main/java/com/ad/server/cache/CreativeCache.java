package com.ad.server.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import com.ad.server.pojo.Creative;

/**
 * @author sagupta
 */

public class CreativeCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "CREATIVE_CACHE_KEY";

  public final ConcurrentMap creativeCache;

  public CreativeCache() {
    creativeCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(10109);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, List<Creative>> data = getCache(cache, Map.class);
//    if (data != null && !data.isEmpty()) {
      creativeCache.put(keys.get(1), data);
      version.incrementAndGet();
      creativeCache.remove(keys.get(0));

//    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.creativeCache.get(getKey(CACHE_KEY, version));
  }
}
