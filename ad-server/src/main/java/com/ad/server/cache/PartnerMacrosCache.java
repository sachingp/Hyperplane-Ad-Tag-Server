package com.ad.server.cache;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */


@Slf4j
@Data
public class PartnerMacrosCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "CREATIVE_CACHE_KEY";

  public final ConcurrentMap partnerMacrosCache;

  public PartnerMacrosCache() {
    partnerMacrosCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(101021);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, Map<String, String>> data = getCache(cache, Map.class);
    if (data != null) {
      partnerMacrosCache.put(keys.get(1), data);
      version.incrementAndGet();
      partnerMacrosCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.partnerMacrosCache.get(getKey(CACHE_KEY, version));
  }


}
