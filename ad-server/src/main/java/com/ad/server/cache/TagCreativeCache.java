package com.ad.server.cache;

import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sagupta
 */


@Slf4j
@Data
public class TagCreativeCache implements Cache {

  public final ConcurrentHashMap<String, Integer> tagCreativeMapCache;

  public TagCreativeCache() {
    tagCreativeMapCache = new ConcurrentHashMap<>();
  }

  @Override
  public void build() {

    String tagCreativeCache = AdServerRedisClient.getInstance()
        .get(CACHE.TAG_GUID_CREATIVE_CACHE_KEY);

    log.info("Tag Creative Cache :: {}", tagCreativeCache);

  }


}
