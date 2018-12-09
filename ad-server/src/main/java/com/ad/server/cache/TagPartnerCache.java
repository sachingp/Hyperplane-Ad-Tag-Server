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
public class TagPartnerCache implements Cache {

  public final ConcurrentHashMap<String, Integer> tagPartnerMapCache;

  public TagPartnerCache() {

    tagPartnerMapCache = new ConcurrentHashMap<>();
  }

  @Override
  public void build() {

    String tagPartnerMapCache = AdServerRedisClient.getInstance()
        .get(CACHE.PARTNER_MACROS_CACHE_KEY);

    log.info("Partner Cache :: {}", tagPartnerMapCache);

  }

}
