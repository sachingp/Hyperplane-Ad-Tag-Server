package com.ad.server.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import com.ad.server.pojo.CreativeTag;

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
    log.debug("Tag Creative Cache Load Process ::");
    List<String> keys = getKeys(CACHE_KEY, version);
    log.debug("Tag Creative Cache Load Keys  :: {}", keys);
    Map<String, Integer> data = getCache(cache, Map.class);
    log.debug("Tag Creative Cache Load Process :: data {}", data);
    if (data != null && !data.isEmpty()) {
      tagCreativeCache.put(keys.get(1), data);
      version.incrementAndGet();
      tagCreativeCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    log.debug("Tag Creative cache version :: {}", getKey(CACHE_KEY, version));
    Map<String, CreativeTag> cache = (Map<String, CreativeTag>) this.tagCreativeCache
        .get(getKey(CACHE_KEY, version));
    log.debug("Tag Creative cache:: {}", cache);
    if (adContext != null && cache != null && cache.containsKey(adContext.getTag())) {
      return true;
    }
    return false;
  }

  public <T> T getCache(final Class<T> type) {
    log.debug("Key :: {}", getKey(CACHE_KEY, version));
    return (T) this.tagCreativeCache.get(getKey(CACHE_KEY, version));
  }
}
