package com.ad.server.cache;

import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sagupta
 */


@Slf4j
@Data
public class PartnerMacrosCache implements Cache {

  public final ConcurrentHashMap<Integer, Map<String, String>> partnerMacrosCache;

  public PartnerMacrosCache() {
    partnerMacrosCache = new ConcurrentHashMap<>();

  }

  @Override
  public void build() {

    String partnerMacrosCache = AdServerRedisClient.getInstance()
        .get(CACHE.PARTNER_MACROS_CACHE_KEY);

    log.info("Partner Macros Cache :: {}", partnerMacrosCache);

  }
}
