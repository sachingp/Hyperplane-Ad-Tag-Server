package com.ad.server.cache;


import com.ad.server.context.AdContext;
import com.ad.util.constants.AdServerConstants.CACHE;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */

@Data
@Slf4j
public class CreativeCountryCache extends AbstractCache {


  public final AtomicInteger version;

  private static final String CACHE_KEY = "CREATIVE_COUNTRY_CACHE_KEY";

  public final Map<String, Map<Integer, Map<String, List<String>>>> creativeCountryCache;

  public CreativeCountryCache() {
    creativeCountryCache = new ConcurrentHashMap<>();
    version = new AtomicInteger(10106);
  }

  @Override
  public <T> void build(final T cache) {

    List<String> keys = getKeys(CACHE_KEY, version);
    Map<Integer, Map<String, List<String>>> data = getCache(cache, Map.class);
    if (data != null && !data.isEmpty()) {
      creativeCountryCache.put(keys.get(1), data);
      version.incrementAndGet();
      creativeCountryCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {

    if (adContext.getCreativeId() > 0) {

      Map<String, List<String>> cache = this.creativeCountryCache.get(getKey(CACHE_KEY, version))
          .get(adContext.getCreativeId());
      if (cache != null) {
        List<String> countryInclusions = cache.get(CACHE.INCLUSION);

        List<String> countryExclusions = cache.get(CACHE.EXCLUSION);

        log.info("Countries Cache :: country inclucions :: {}  , country exclusions :: {} ",
            countryInclusions, countryExclusions);

        if (countryInclusions == null || countryInclusions.isEmpty()) {
          if (countryExclusions == null || countryExclusions.isEmpty()) {
            return true;
          } else if (countryExclusions.contains(!Strings
              .isNullOrEmpty(adContext.getCountry()))) {
            return false;
          } else {
            return true;
          }
        }

        if (!countryInclusions.isEmpty() && !Strings
            .isNullOrEmpty(adContext.getCountry())) {
          if (countryInclusions.contains(adContext.getCountry())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.creativeCountryCache.get(getKey(CACHE_KEY, version));
  }
}
