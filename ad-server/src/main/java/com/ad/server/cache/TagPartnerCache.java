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
public class TagPartnerCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "TAG_PARTNER_CACHE_KEY";

  public final ConcurrentMap tagPartnerCache;

  public TagPartnerCache() {
    tagPartnerCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(101015);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<String, Integer> data = getCache(cache, Map.class);
    if (data != null) {
      tagPartnerCache.put(keys.get(1), data);
      version.incrementAndGet();
      tagPartnerCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.tagPartnerCache.get(getKey(CACHE_KEY, version));
  }

}
