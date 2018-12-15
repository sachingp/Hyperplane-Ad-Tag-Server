package com.ad.server.cache;

import static com.ad.util.ObjectUtil.combine;
import static com.ad.util.ObjectUtil.getBytes;
import static com.ad.util.ObjectUtil.readObject;

import com.ad.server.cache.exception.CacheException;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Advertiser;
import com.ad.server.pojo.Campaign;
import com.ad.server.pojo.Creative;
import com.ad.server.repo.AccountRepo;
import com.ad.server.repo.AdPartnerRepo;
import com.ad.server.repo.AdvertiserRepo;
import com.ad.server.repo.CampaignRepo;
import com.ad.server.repo.CreativeRepo;
import com.ad.server.repo.CreativeTagRepo;
import com.ad.server.repo.MacrosRepo;
import com.ad.util.client.AdServerRedisClient;
import lombok.Getter;
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
@Getter
public class AdServerCache {

  private static volatile AdServerCache adServerCache = null;
  // TODO - This can me moved to Offheap Cache - MapDBd
  public static Cache accountCache;
  public static Cache advertiserCache;
  public static Cache campaignCache;
  public static Cache creativeCache;
  public static Cache creativeDetailsCache;
  public static Cache tagCreativeCache;
  public static Cache creativeCountryCache;
  public static Cache tagPartnerCache;
  public static Cache partnerMacrosCache;
  private final ScheduledExecutorService executorService;
  private final int INITIAL_DELAY = 300;
  private final int SCHEDULE_TIME_PERIOD = 1800; // 15T Min

  private static final byte[] partKeyBytes = "-parts".getBytes();
  private static final byte[] partIndexKeyBytes = "-part-".getBytes();
  private static AdServerRedisClient client = null;


  private AdServerCache() {
    accountCache = new AccountCache();
    advertiserCache = new AdvertiserCache();
    campaignCache = new CampaignCache();
    creativeCache = new CreativeCache();
    creativeDetailsCache = new CreativeDetailsCache();
    tagCreativeCache = new TagCreativeCache();
    creativeCountryCache = new CreativeCountryCache();
    tagPartnerCache = new TagPartnerCache();
    partnerMacrosCache = new PartnerMacrosCache();
    client = AdServerRedisClient.getInstance();
    update();
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

    ACTIVE_ACCOUNT(AccountRepo.ACTIVE_ACCOUNTS) {
      public void load() throws CacheException {
        final Map<String, Account> accounts = read(ACTIVE_ACCOUNT, Map.class);
        if (accounts != null) {
          log.trace("Accounts from cache: {}", accounts);
          accountCache.build(accounts);
        }
      }
    },
    ACCOUNT_ADVERTISER(AdvertiserRepo.ACCOUNT_ADVERTISER) {
      public void load() throws CacheException {
        final Map<Integer, List<Advertiser>> advertisers = read(ACCOUNT_ADVERTISER, Map.class);
        if (advertisers != null) {
          log.trace("Advertisers from cache: {}", advertisers);
          advertiserCache.build(advertisers);
        }
      }
    },
    ADVERTISER_CAMPAIGN(CampaignRepo.ADVERTISER_CAMPAIGN) {
      public void load() throws CacheException {
        final Map<Integer, List<Campaign>> campaigns = read(ADVERTISER_CAMPAIGN, Map.class);
        log.trace("Campaigns from cache: {}", campaigns);
        campaignCache.build(campaigns);
      }
    },
    CAMPAIGN_CREATIVE(CreativeRepo.CAMPAIGN_CREATIVE) {
      public void load() throws CacheException {
        final Map<Integer, List<Creative>> creatives = read(CAMPAIGN_CREATIVE, Map.class);
        if (creatives != null) {
          log.trace("Creatives from cache: {}", creatives);
          creativeCache.build(creatives);
        }
      }
    },
    ACTIVE_CREATIVE(CreativeRepo.ACTIVE_CREATIVE) {
      public void load() throws CacheException {
        final Map<Integer, Creative> creatives = read(ACTIVE_CREATIVE, Map.class);
        if (creatives != null) {
          log.info("All Active Creatives from cache: {}", creatives);
          creativeDetailsCache.build(creatives);
        }
      }
    },
    ACTIVE_TAGS(CreativeTagRepo.TAG_GUID_CREATIVE) {
      public void load() throws CacheException {
        final Map<String, Integer> activeTagGuids = read(ACTIVE_TAGS, Map.class);
        if (activeTagGuids != null) {
          log.trace("Active Tag Guis from cache: {}", activeTagGuids);
          tagCreativeCache.build(activeTagGuids);
        }
      }
    },
    TAG_PARTNER(AdPartnerRepo.ACCOUNT_PARTNER) {
      public void load() throws CacheException {
        final Map<String, Integer> tagPartner = read(TAG_PARTNER, Map.class);
        log.trace("Tag Partner from cache: {}", tagPartner);
        tagPartnerCache.build(tagPartner);
      }
    },
    PARTNER_MACROS(MacrosRepo.PARTNER_MACRO) {
      public void load() throws CacheException {
        final Map<Integer, Map<String, String>> tagPartner = read(PARTNER_MACROS, Map.class);
        if (tagPartner != null) {
          log.trace("Partner Macros from cache: {}", tagPartner);
          partnerMacrosCache.build(tagPartner);
        }
      }
    },
    CREATIVE_COUNTRY(CreativeRepo.CREATIVE_COUNTRY) {
      public void load() throws CacheException {
        final Map<Integer, Map<String, List<String>>> creativeCountry = read(CREATIVE_COUNTRY,
            Map.class);
        if (creativeCountry != null) {
          log.trace("Creative Country from cache: {}", creativeCountry);
          creativeCountryCache.build(creativeCountry);
        }
      }
    };

    private final String name;

    private CacheType(final String name) {
      this.name = name;
    }

    private static <T> T read(final CacheType cache, final Class<T> type) throws CacheException {
      final String name = cache.name;
      final String key = cache.name;
      log.debug("Cache Key :: {}", key);
      try {
        if (key == null || key.trim().isEmpty()) {
          log.error("Key is null for {}", name);
          return null;
        }
        final byte[] partRecord = client.get(combine(getBytes(key), partKeyBytes));
        log.debug("Part Record ::{}", partRecord);
        if (partRecord == null) {
          log.debug("Parting record not available. Fetching full data for: {}", key);
          final byte[] record = client.get(getBytes(key));
          if (record != null) {
            log.debug("Full record data available ::");
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
