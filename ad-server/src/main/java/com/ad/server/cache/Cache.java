package com.ad.server.cache;

import com.ad.server.context.AdContext;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */

public interface Cache {

  public <T> void build(final T cache);

  public boolean evaluate(final AdContext adContext);

  public <T> T getCache(final Object cache, final Class<T> type);

  public List<String> getKeys(final String key, final AtomicInteger version);

  public String getKey(final String key, final AtomicInteger version);

  public <T> T getCache(final Class<T> type);

}
