package com.ad.services.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ad.server.Cache;
import com.ad.services.exception.AdServicesException;

@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unchecked"})
public class CacheRegistry<C extends Cache> {

  private final Map<String, List<C>> registry;

  @Autowired
  private ApplicationContext context;

  private CacheRegistry() {
    registry = new HashMap<>();
  }

  public List<C> scan(final String packageName) throws AdServicesException {
    if (registry.containsKey(packageName)) {
      log.info("Returning Caches for packageName: {}", packageName);
      return registry.get(packageName);
    }
    try {
      final String path = packageName.replace('.', '/');
      log.info("Accessing resources from path: {}", path);
      if (log.isDebugEnabled()) {
        final String[] repos = context.getBeanNamesForAnnotation(Repository.class);
        log.debug("Repos: {}", Arrays.toString(repos));
      }
      final List<C> caches = new ArrayList<>();
      registry.put(packageName, caches);
      final Resource[] resources = context.getResources("classpath*:" + path + "/*.class");
      for (final Resource resource : resources) {
        final String fileName = resource.getFilename();
        final Class clazz = Class
            .forName(packageName + '.' + fileName.substring(0, fileName.length() - 6));
        log.info("Loaded class of type: {}", clazz);
        if (Cache.class.isAssignableFrom(clazz)) {
          log.info("Accessing bean for type: {}", clazz);
          final Repository repository = (Repository) clazz.getAnnotation(Repository.class);
          if (repository == null) {
            log.warn("No bean name configured for: {}", clazz);
            continue;
          }
          final String beanName = repository.value();
          if (beanName == null || beanName.trim().isEmpty()) {
            log.warn("No bean name configured for: {}", clazz);
            continue;
          }
          caches.add((C) context.getBean(beanName));
        }
      }
      return caches;
    } catch (IOException | ClassNotFoundException e) {
      log.error(e.getMessage(), e);
      throw new AdServicesException(e);
    }
  }

  public List<C> list(final String packageName) {
    return registry.get(packageName);
  }

}