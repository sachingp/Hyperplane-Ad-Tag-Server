package com.ad.server.cache;

import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.CACHE;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sagupta
 */

@Slf4j
public class AdServerCache {

  private final ScheduledExecutorService executorService;
  private final int INITIAL_DELAY = 0;
  private final int SCHEDULE_TIME_PERIOD = 120; // 2 Min
  private static volatile AdServerCache adServerCache = null;
  // TODO - This can me moved to Offheap Cache - MapDB


  public final CreativeCountryCache creativeCountryCache;
  public final ActiveTagsCache allActiveTagCache;
  public final TagCreativeCache tagCreativeMapCache;
  public final TagPartnerCache tagPartnerMapCache;
  public final PartnerMacrosCache partnerMacrosCache;


  private AdServerCache() {

    creativeCountryCache = new CreativeCountryCache();
    allActiveTagCache = new ActiveTagsCache();
    tagCreativeMapCache = new TagCreativeCache();
    tagPartnerMapCache = new TagPartnerCache();
    partnerMacrosCache = new PartnerMacrosCache();

    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new CacheScheduler(this),
        INITIAL_DELAY, SCHEDULE_TIME_PERIOD, TimeUnit.SECONDS);

  }

  public static AdServerCache getInstance() {

    if (adServerCache == null) {
      synchronized (AdServerCache.class) {
        if (adServerCache == null) {
          adServerCache = new AdServerCache();
        }
      }
    }

    return adServerCache;

  }


  private void buildTagPartnerCache() {

    String tagPartnerCache = AdServerRedisClient.getInstance()
        .get(CACHE.TAG_PARTNER_CACHE_KEY);

    log.info("Tag Partner Cache :: {}", tagPartnerCache);

  }


  // loading the file to cache
  private void update() {
    log.info("Cache Update Started : " + new Date());
    try {
      allActiveTagCache.build();
      creativeCountryCache.build();
      tagCreativeMapCache.build();
      tagPartnerMapCache.build();
      partnerMacrosCache.build();

    } catch (Exception e) {
      log.error("Error in updating cache :: {} ", e);
    }
  }

  private class CacheScheduler implements Runnable {

    private AdServerCache service;

    public CacheScheduler(AdServerCache service) {
      this.service = service;
    }

    @Override
    public void run() {
      this.service.update();

    }
  }
}
