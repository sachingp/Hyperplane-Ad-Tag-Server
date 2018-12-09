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

@Data
@Slf4j
public class CreativeCountryCache implements Cache {

  public final ConcurrentHashMap<Integer, Set<String>> creativeInclusionsCountryCache;
  public final ConcurrentHashMap<Integer, Set<String>> creativeExclusionsCountryCache;

  public CreativeCountryCache() {
    creativeExclusionsCountryCache = new ConcurrentHashMap<>();
    creativeInclusionsCountryCache = new ConcurrentHashMap<>();
  }


  /**
   * build country cache for each tag
   */

  @Override
  public void build() {

    String tagCountryCache = AdServerRedisClient.getInstance().get(CACHE.ALL_TAG_COUNTRY_CACHE_KEY);
    log.info("Country Cahe :: {}", tagCountryCache);

  }

}
