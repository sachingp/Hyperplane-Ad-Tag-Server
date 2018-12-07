package com.ad.server.cache;

import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sagupta
 */

@Slf4j
public class TagCache {

  private final ScheduledExecutorService executorService;
  private final int INITIAL_DELAY = 0;
  private final int SCHEDULE_TIME_PERIOD = 120; // 2 Min
  private static volatile TagCache tagCache = null;
  // TODO - This can me moved to Offheap Cache - MapDB


  public final ConcurrentHashMap<Integer, Set<String>> creativeCountryCache;
  public final ConcurrentHashMap<String, Set<String>> allActiveTagCache;
  public final ConcurrentHashMap<String, Integer> tagCreativeMapCache;
  public final ConcurrentHashMap<String, Integer> tagPartnerMapCache;
  public final ConcurrentHashMap<Integer, Map<String, String>> partnerMacrosCache;


  private TagCache() {

    creativeCountryCache = new ConcurrentHashMap<>();
    allActiveTagCache = new ConcurrentHashMap<>();
    tagCreativeMapCache = new ConcurrentHashMap<>();
    tagPartnerMapCache = new ConcurrentHashMap<>();
    partnerMacrosCache = new ConcurrentHashMap<>();

    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new CacheScheduler(this),
        INITIAL_DELAY, SCHEDULE_TIME_PERIOD, TimeUnit.SECONDS);

  }

  public static TagCache getInstance() {

    if (tagCache == null) {
      synchronized (TagCache.class) {
        if (tagCache == null) {
          tagCache = new TagCache();
        }
      }
    }

    return tagCache;

  }

  /**
   * build country cache for each tag
   */

  private void buildTagCountryCache() {

    String tagCountryCache = AdServerRedisClient.getInstance().get(CACHE.ALL_TAG_COUNTRY_CACHE_KEY);

  }

  private void buildAllActiveTagGuidCache() {

    String tagCountryCache = AdServerRedisClient.getInstance()
        .get(CACHE.ALL_ACTIVE_TAG_GUID_CACHE_KEY);

  }


  private void buildGuidCreativeCache() {

    String guidCreativeCache = AdServerRedisClient.getInstance()
        .get(CACHE.TAG_GUID_CREATIVE_CACHE_KEY);

  }

  private void buildTagPartnerCache() {

    String guidCreativeCache = AdServerRedisClient.getInstance()
        .get(CACHE.TAG_PARTNER_CACHE_KEY);

  }

  private void buildPartnerMacrosCache() {

    String guidCreativeCache = AdServerRedisClient.getInstance()
        .get(CACHE.PARTNER_MACROS_CACHE_KEY);

  }

  // loading the file to cache
  private void update() {
    log.info("Cache Update Started : " + new Date());
    try {
      buildAllActiveTagGuidCache();
      buildTagCountryCache();
      buildGuidCreativeCache();
      buildTagPartnerCache();
      buildPartnerMacrosCache();

    } catch (Exception e) {
      log.error("Error in updating cache :: {} ", e);
    }
  }

  private class CacheScheduler implements Runnable {

    private TagCache service;

    public CacheScheduler(TagCache service) {
      this.service = service;
    }

    @Override
    public void run() {
      this.service.update();

    }
  }
}
