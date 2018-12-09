package com.ad.services.cache;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ad.server.Cache;
import com.ad.services.cache.buider.RedisCacheBuilder;
import com.ad.services.exception.AdServicesException;

@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class CacheInitializer {

  @Value("#{'${cache.scan.package}'.split(',')}")
  private List<String> cachePackages;

  @Autowired
  private RedisCacheBuilder builder;

  @Autowired
  private CacheRegistry registry;

  @PostConstruct
  public void init() throws AdServicesException {
    log.info("Initializing cache packages: {}", cachePackages);
    for (final String cachePackage : cachePackages) {
      final List<Cache> caches = registry.scan(cachePackage);
      caches.parallelStream().forEach(cache -> {
        try {
          builder.build(cache);
        } catch (final AdServicesException e) {
          log.error(e.getMessage(), e);
        }
      });
    }
  }

}
