package com.ad.server.cache;


import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sagupta
 */
@Slf4j
@Data
public class ActiveTagsCache implements Cache {

  public final ConcurrentHashMap<String, Set<String>> allActiveTagCache;

  public ActiveTagsCache() {
    allActiveTagCache = new ConcurrentHashMap<>();
  }

  @Override
  public void build() {

    String allActiveTagCache = AdServerRedisClient.getInstance()
        .get(CACHE.ALL_ACTIVE_TAG_GUID_CACHE_KEY);
    log.info("All Active Tag Cache :: {}", allActiveTagCache);

  }

}
