package com.ad.services.cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.ad.server.Cache;
import com.ad.server.Cacheable;
import com.ad.services.cache.type.AbstractCacheType;
import com.ad.services.cache.type.Key;
import com.ad.services.cache.type.Value;
import com.ad.services.exception.AdServicesException;
import com.ad.util.reflect.ReflectionException;
import com.ad.util.reflect.ReflectionUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@Component
public abstract class AbstractCacheBuilder<T> implements CacheBuilder<Key, Value> {

  //  private static final Integer DEFAULT_STATUS = 1;
  private static final Map<String, CacheStats> statistics = new HashMap<>();
  @org.springframework.beans.factory.annotation.Value("#{'${cache.names}'.split(',')}")
  private Set<String> cacheNames;

  public void build(final Cache cache) throws AdServicesException {
    try {
      final List<Method> methods = ReflectionUtil.getMethods(cache.getType(), Cacheable.class);
      for (final Method method : methods) {
        if (method.isAnnotationPresent(Cacheable.class)) {
          final Cacheable cacheable = method.getAnnotation(Cacheable.class);
          final String cacheName = cacheable.name();
          if (!cacheNames.contains(cacheName)) {
            log.info("Cache is opted out for loading. Not loading cache {}", cacheName);
            continue;
          }
          log.info("Starting cache build for: {}", cacheName);
          final CacheStats stats = new CacheStats(cacheName);
          statistics.put(cacheName, stats);
          stats.start();
          try {
            final String[] keys = cacheable.key();
            if (keys == null || keys.length == 0) {
              final String message = "Key cannot be empty for Cacheable on method: " + method;
              stats.fail(message);
              throw new AdServicesException(message);
            }
            final String[] values = cacheable.value();
            final Class keyType = cacheable.keyType();
            final Class valueType = cacheable.valueType();
            final String methodName = cacheable.custom();
            if (keyType == Object.class && methodName.isEmpty()) {
              final String message =
                  "One of keyType or custom should be present for Cacheable on method: " + method;
              stats.fail(message);
              throw new AdServicesException(message);
            }
            final List<T> results = (List<T>) method.invoke(cache); // FIXME - handle args
            if (results == null) {
              final String message = "Invalid results: " + method;
              stats.fail(message);
              throw new AdServicesException(message);
            }
            if (methodName.isEmpty()) {
              log.info("Building standard cache for type {} with cache {} of size {}",
                  cache.getType(), cacheName, results.size());
              prepareStandardCache(cacheable, cacheName, stats, keys, values, keyType, valueType,
                  results);
            } else {
              prepareCustomCache(cache, cacheName, methodName, results, cacheable, stats);
            }
          } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ReflectionException
              | NoSuchMethodException | SecurityException e) {
            log.error(e.getMessage(), e);
            stats.fail(e.getMessage());
            throw new AdServicesException(e);
          } finally {
            stats.end();
            log.info(stats.toString());
          }
        }
      }
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage(), e);
      throw new AdServicesException(e);
    }
  }

  private void prepareStandardCache(final Cacheable cacheable, final String cacheName,
      final CacheStats stats,
      final String[] keys, final String[] values, final Class keyType, final Class valueType,
      final List<T> results)
      throws ReflectionException, AdServicesException {
    final Map whole = new HashMap();
    for (final T result : results) {
      stats.inc();
      final Class<? extends Object> resultType = result.getClass();
      final Key asKey;
      if (keys.length == 1 && "NULL".equals(keys[0]) && resultType.equals(keyType)) {
        asKey = null;
      } else {
        asKey = new Key(resultType, keyType, keys);
        prepareValuesByNames(keys, result, asKey);
      }
      final Value asValue;
      final Object key = asKey == null ? cacheName : asKey.get();
      if (values == null || values.length == 0 || valueType.equals(resultType)) {
        asValue = new Value(result);
      } else if (BitSet.class.equals(valueType)) {
        if (values.length > 1) {
          throw new AdServicesException(
              "Invalid cache configuration. BitSet value should have only one value attribute");
        }
        asValue = null;
        final BitSet bits;
        if (cacheable.whole()) {
          if (whole.containsKey(key)) {
            bits = (BitSet) whole.get(key);
          } else {
            bits = new BitSet();
            whole.put(key, bits);
          }
        } else {
          final T existing = read(cacheName, asKey);
          if (existing == null) {
            bits = new BitSet();
          } else {
            bits = (BitSet) existing;
          }
        }
        final Object bitValue = ReflectionUtil.getFieldValue(result.getClass(), result, values[0]);
        final int intValue;
        if (bitValue == null || (intValue = ((int) bitValue)) < 0) {
          continue;
        }
        bits.set(intValue);
        continue;
      } else {
        asValue = new Value(resultType, valueType, values);
        prepareValuesByNames(values, result, asValue);
      }
      if (cacheable.whole()) {
        whole.put(key, asValue.get());
        log.debug("Skipping individual item write to support whole upload");
        continue;
      }
      write(cacheName, asKey, asValue);
    }
    if (cacheable.whole()) {
      log.info("Writing whole cache for name {}", cacheName);
      write(cacheName, new Key(cacheName), new Value(whole));
    }
  }

  private void prepareCustomCache(final Cache cache, final String cacheName,
      final String methodName, final List<T> results, final Cacheable cacheable,
      final CacheStats stats)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, AdServicesException {
    log.info("Invoking custom {} for type {} with results of size {}", methodName, cache.getType(),
        results.size());
    final Method customMethod = cache.getType().getMethod(methodName, List.class);
    final Map customCache = (Map) customMethod.invoke(cache, results);
    if (customCache == null) {
      return;
    }
    if (cacheable.whole()) {
      log.info("Writing whole cache for name {}", cacheName);
      stats.inc(customCache == null ? 0 : customCache.size());
      write(cacheName, new Key(cacheName), new Value(customCache));
      return;
    }
    customCache.forEach((key, value) -> {
      if (value == null) {
        return;
      }
      try {
        write(cacheName, new Key(key), new Value(value));
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    });
  }

  private void prepareValuesByNames(final String[] fields, final T result,
      final AbstractCacheType type)
      throws ReflectionException,
      AdServicesException {
    final List values = new ArrayList();
    for (final String field : fields) {
      values.add(ReflectionUtil.getFieldValue(result.getClass(), result, field));
    }
    type.set(values);
  }

  public void delta(final Cache cache, final Date lastModifiedOn) {
    
  }


  protected abstract void write(String name, Key key, Value value) throws AdServicesException;

  protected abstract T read(String name, Key key) throws AdServicesException;

  public String getStats() {
    final StringBuilder builder = new StringBuilder("Cache Statistics:\n");
    statistics.forEach((k, v) -> builder.append("\n\n").append(v));
    return builder.toString();
  }

  private static class CacheStats {

    private final String name;
    private Date startedOn;
    private Date completedOn;
    private int count;
    private String error;

    private CacheStats(final String name) {
      this.name = name;
    }

    private void inc() {
      count++;
    }

    private void inc(final int count) {
      this.count += count;
    }

    private void start() {
      startedOn = new Date();
    }

    private void end() {
      completedOn = new Date();
    }

    private void fail(final String message) {
      error = message;
    }

    public String toString() {
      final StringBuilder builder = new StringBuilder();
      builder.append("Statistics for ").append(name).append(":\n");
      builder.append("Started - ").append(startedOn).append("\n");
      builder.append("Completed - ").append(completedOn).append("\n");
      if (error == null) {
        builder.append("Total - ").append(count).append("\n");
      } else {
        builder.append("Failed - ").append(error).append("\n");
      }
      final long diff = completedOn.getTime() - startedOn.getTime();
      builder.append("Time - ").append(diff).append(" milliseconds");
      return builder.toString();
    }
  }

}
