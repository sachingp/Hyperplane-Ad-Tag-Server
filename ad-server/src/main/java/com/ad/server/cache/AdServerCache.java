package com.ad.server.cache;

import static com.ad.util.ObjectUtil.combine;
import static com.ad.util.ObjectUtil.getBytes;
import static com.ad.util.ObjectUtil.readObject;

import com.ad.server.cache.exception.CacheException;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Advertiser;
import com.ad.server.pojo.Campaign;
import com.ad.server.pojo.Creative;
import com.ad.util.client.AdServerRedisClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author sagupta
 */

@Slf4j
public class AdServerCache {

  private static volatile AdServerCache adServerCache = null;
  public final CreativeCountryCache creativeCountryCache;
  public final ActiveTagsCache allActiveTagCache;
  public final TagCreativeCache tagCreativeMapCache;
  // TODO - This can me moved to Offheap Cache - MapDB
  public final TagPartnerCache tagPartnerMapCache;
  public final PartnerMacrosCache partnerMacrosCache;
  private final ScheduledExecutorService executorService;
  private final int INITIAL_DELAY = 0;
  private final int SCHEDULE_TIME_PERIOD = 120; // 2 Min

  private static final byte[] partKeyBytes = "-parts".getBytes();
  private static final byte[] partIndexKeyBytes = "-part-".getBytes();
  private static AdServerRedisClient client = null;


  private AdServerCache() {

    creativeCountryCache = new CreativeCountryCache();
    allActiveTagCache = new ActiveTagsCache();
    tagCreativeMapCache = new TagCreativeCache();
    tagPartnerMapCache = new TagPartnerCache();
    partnerMacrosCache = new PartnerMacrosCache();
    client = AdServerRedisClient.getInstance();
    executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new CacheScheduler(this),
        INITIAL_DELAY, SCHEDULE_TIME_PERIOD, TimeUnit.SECONDS);
    log.info("Ad Server Cache Initialized :: {} ", client);

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

  // loading the file to cache
  private void update() {
    log.info("Cache Update Started :: {} ", new Date());
    try {

      /**
       allActiveTagCache.build();
       creativeCountryCache.build();
       tagCreativeMapCache.build();
       tagPartnerMapCache.build();
       partnerMacrosCache.build();
       */

      for (CacheType cacheType : CacheType.values()) {
        cacheType.load();
      }

      log.info("Cache Update Completed :: {}", new Date());
    } catch (Exception e) {
      log.error("Error in updating cache ::", e);
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

  public enum CacheType {

    ACTIVE_ACCOUNT("active-account") {
      public void load() throws CacheException {
        final Map<String, Account> accounts = read(ACTIVE_ACCOUNT, Map.class);
      }
    },
    ACCOUNT_ADVERTISER("account-advertiser") {
      public void load() throws CacheException {
        final Map<Integer, List<Advertiser>> advertisers = read(ACCOUNT_ADVERTISER, Map.class);
      }
    },
    ADVERTISER_CAMPAIGN("advertiser-campaign") {
      public void load() throws CacheException {
        final Map<Integer, List<Campaign>> campaigns = read(ADVERTISER_CAMPAIGN, Map.class);
      }
    },
    CAMPAIGN_CREATIVE("campaign-creative") {
      public void load() throws CacheException {
        final Map<Integer, List<Creative>> creatives = read(CAMPAIGN_CREATIVE, Map.class);
      }
    };

    private final String name;

    private CacheType(final String name) {
      this.name = name;
    }

    private static <T> T read(final CacheType cache, final Class<T> type) throws CacheException {
      final String name = cache.name;
      final String key = cache.name;
      log.info("Cache Key :: {}", key);
      try {
        if (key == null || key.trim().isEmpty()) {
          log.error("Key is null for {}", name);
          return null;
        }
        final byte[] partRecord = client.get(combine(getBytes(key), partKeyBytes));
        log.info("Part Record ::{}", partRecord);
        if (partRecord == null) {
          log.info("Parting record not available. Fetching full data for: {}", key);
          final byte[] record = client.get(getBytes(key));
          if (record != null) {
            return (T) readObject(Object.class, (byte[]) record);
          } else {
            return null;
          }
        }
        final int parts = (int) readObject(Object.class, partRecord);
        log.debug("Parting record available. Fetching data parts: {}", key);
        int i = 0;
        byte[] wholeBytes = new byte[0];
        while (i < parts) {
          final byte[] record = client
              .get(combine(getBytes(key), partIndexKeyBytes, new byte[]{(byte) i}));
          log.debug("Fetching part data for: {}", key + new String(partIndexKeyBytes) + i);
          wholeBytes = combine(wholeBytes, record);
          i++;
        }
        return (T) readObject(Object.class, wholeBytes);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw new CacheException(e.getMessage(), e);
      }
    }

    abstract void load() throws CacheException;

  }
}
