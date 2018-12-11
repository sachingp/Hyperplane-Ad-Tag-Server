package com.ad.server.cache;

import com.ad.server.context.AdContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */


@Slf4j
@Data
public class PartnerMacrosCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "CREATIVE_CACHE_KEY";

  public final Map<String, Map<Integer, Map<String, String>>> partnerMacrosCache;

  public PartnerMacrosCache() {
    partnerMacrosCache = new ConcurrentHashMap<>();
    version = new AtomicInteger(101021);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, Map<String, String>> data = getCache(cache, Map.class);
    if (data != null && !data.isEmpty()) {
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
