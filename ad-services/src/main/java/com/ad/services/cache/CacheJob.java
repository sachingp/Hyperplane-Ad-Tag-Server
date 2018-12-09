package com.ad.services.cache;

import lombok.extern.slf4j.Slf4j;

import com.ad.services.exception.AdServicesException;

@Slf4j
public class CacheJob implements Runnable {

  private final CacheInitializer initializer;

  public CacheJob(final CacheInitializer initializer) {
    this.initializer = initializer;
  }

  public void run() {
    try {
      initializer.init();
    } catch (AdServicesException e) {
      log.error(e.getMessage(), e);
    }
  }

}