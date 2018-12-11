package com.ad.server.cache;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */


@Slf4j
@Getter
public class TagCreativeCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "TAG_CREATIVE_CACHE_KEY";

  public final ConcurrentMap tagCreativeCache;

  public TagCreativeCache() {
    tagCreativeCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(10109);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<String, Integer> data = getCache(cache, Map.class);
    if (data != null && !data.isEmpty()) {
      tagCreativeCache.put(keys.get(1), data);
      version.incrementAndGet();
      tagCreativeCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    Map<String, Integer> cache = (Map<String, Integer>) this.tagCreativeCache
        .get(getKey(CACHE_KEY, version));
    if (adContext != null && cache != null && cache.containsKey(adContext.getTag())) {
      return true;
    }
    return false;
  }

  public <T> T getCache(final Class<T> type) {
    log.info("Key :: {}", getKey(CACHE_KEY, version));
    return (T) this.tagCreativeCache.get(getKey(CACHE_KEY, version));
  }
}
