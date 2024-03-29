package com.ad.server.cache;

import com.ad.server.context.AdContext;
import com.ad.server.mapdb.MapDbSystem;
import com.ad.server.pojo.Campaign;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */

@Slf4j
public class CampaignCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "CAMPAIGN_CACHE_KEY";

  public final ConcurrentMap campaignCache;

  public CampaignCache() {
    campaignCache = MapDbSystem.getInstance().getDb().hashMap("map").createOrOpen();
    version = new AtomicInteger(10106);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, List<Campaign>> data = getCache(cache, Map.class);
    if (data != null) {
      campaignCache.put(keys.get(1), data);
      version.incrementAndGet();
      campaignCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.campaignCache.get(getKey(CACHE_KEY, version));
  }
}
