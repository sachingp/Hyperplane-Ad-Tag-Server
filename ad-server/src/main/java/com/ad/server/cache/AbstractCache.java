package com.ad.server.cache;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sagupta
 */

@Slf4j
@Getter
public abstract class AbstractCache implements Cache {

  @Override
  public <T> T getCache(final Object cache, final Class<T> type) {
    return (T) cache;
  }

  @Override
  public List<String> getKeys(final String key, final AtomicInteger version) {
    List<String> keys = new ArrayList<>();
    String currentKey = key + new Integer(version.get()).toString();
    String newKey = key + new Integer(version.get() + 1).toString();
    keys.add(currentKey);
    keys.add(newKey);
    return keys;
  }

  @Override
  public String getKey(final String key, final AtomicInteger version) {
    return key + new Integer(version.get()).toString();
  }
}
