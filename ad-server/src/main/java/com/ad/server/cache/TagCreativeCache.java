package com.ad.server.cache;

import com.ad.server.context.AdContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */


@Slf4j
@Getter
public class TagCreativeCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "TAG_CREATIVE_CACHE_KEY";

  public final Map<String, Map<String, Integer>> tagCreativeCache;

  public TagCreativeCache() {
    tagCreativeCache = new ConcurrentHashMap<>();
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
    if (adContext != null && this.tagCreativeCache.get(getKey(CACHE_KEY, version))
        .containsKey(adContext.getTag())) {
      return true;
    }
    return false;
  }

  public <T> T getCache(final Class<T> type) {
    return (T) this.tagCreativeCache.get(getKey(CACHE_KEY, version));
  }
}
