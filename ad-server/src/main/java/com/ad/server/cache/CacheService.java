package com.ad.server.cache;


import static com.ad.util.ObjectUtil.combine;
import static com.ad.util.ObjectUtil.getBytes;
import static com.ad.util.ObjectUtil.readObject;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.ad.server.cache.exception.CacheException;
import com.ad.server.pojo.Account;
import com.ad.server.pojo.Advertiser;
import com.ad.server.pojo.Campaign;
import com.ad.server.pojo.Creative;
import com.ad.util.client.AdServerRedisClient;

/**
 * @author sagupta
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class CacheService {

  private static final byte[] partKeyBytes = "-parts".getBytes();
  private static final byte[] partIndexKeyBytes = "-part-".getBytes();
  private static final AdServerRedisClient client = AdServerRedisClient.getInstance();

  public CacheService() {
    throw new AssertionError("Error while initializing the class");
  }

  /**
   * @return list of countries targeted to creative.
   */
  public static CreativeCountryCache getCountryCacheData() {

    return AdServerCache.getInstance().creativeCountryCache;
  }

  /**
   * @return all active tags.
   */

  public static Cache getAllActiveTags() {
    return AdServerCache.getInstance().allActiveTagCache;
  }

  /**
   * @return if the tag is active or disabled.
   */

  public static boolean isTagActive(final String tagGuid) {

    return AdServerCache.getInstance().allActiveTagCache != null && !AdServerCache
        .getInstance().allActiveTagCache.getAllActiveTagCache().isEmpty()
        && AdServerCache.getInstance().allActiveTagCache.allActiveTagCache.get(tagGuid) != null
        ? true : false;

  }

  /**
   * @return get script data for the tag.
   */

  public static String getTagScriptData(String tag) {
    return AdServerRedisClient.getInstance().get(tag);
  }

  /**
   * @return creative Id for the tag.
   */

  public static Integer getCreative(String tag) {
    return AdServerCache.getInstance().tagCreativeMapCache.getTagCreativeMapCache().get(tag);
  }

  public static enum CacheType {

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
      try {
        if (key == null || key.trim().isEmpty()) {
          log.error("Key is null for {}", name);
          return null;
        }
        final byte[] partRecord = client.get(combine(getBytes(key), partKeyBytes));
        if (partRecord == null) {
          log.debug("Parting record not available. Fetching full data for: {}", key);
          final byte[] record = client.get(getBytes(key));
          return (T) readObject(Object.class, (byte[]) record);
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
