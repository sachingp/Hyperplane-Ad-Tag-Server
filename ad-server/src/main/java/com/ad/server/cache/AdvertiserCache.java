package com.ad.server.cache;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import com.ad.server.pojo.Advertiser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */

@Slf4j
public class AdvertiserCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "ADVERTISER_CACHE_KEY";

  public final ConcurrentMap advertiserCache;

  public AdvertiserCache() {
    advertiserCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(10102);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, List<Advertiser>> data = getCache(cache, Map.class);
    if (data != null && !data.isEmpty()) {
      advertiserCache.put(keys.get(1), data);
      version.incrementAndGet();
      advertiserCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.advertiserCache.get(getKey(CACHE_KEY, version));
  }
}
