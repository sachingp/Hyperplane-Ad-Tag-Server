package com.ad.server.cache;

import com.ad.server.context.AdContext;
import com.ad.server.pojo.Account;
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
public class AccountCache extends AbstractCache {

  public final AtomicInteger version;

  public static final String CACHE_KEY = "ACCOUNT_CACHE_KEY";


  public final Map<String, Map<String, Account>> accountCache;

  public AccountCache() {
    accountCache = new ConcurrentHashMap<>();
    version = new AtomicInteger(10101);
  }

  @Override
  public <T> void build(final T cache) {
    List<String> keys = getKeys(CACHE_KEY, version);
    Map<String, Account> data = getCache(cache, Map.class);
    if (data != null && !data.isEmpty()) {
      accountCache.put(keys.get(1), data);
      version.incrementAndGet();
      accountCache.remove(keys.get(0));

    }
  }

  @Override
  public boolean evaluate(final AdContext adContext) {
    return true;
  }

  @Override
  public <T> T getCache(Class<T> type) {
    return (T) this.accountCache.get(getKey(CACHE_KEY, version));
  }
}
