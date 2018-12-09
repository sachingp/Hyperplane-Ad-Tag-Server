package com.ad.services.cache;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ad.services.exception.AdServicesException;

@Slf4j
@Component
public class CacheScheduler {

  @Autowired
  private CacheInitializer initializer;

  @Value("${cache.refresh.interval.in.minutes:1}")
  private int intervalInMins;

  @Value("${cache.refresh.interval.in.seconds}")
  private int intervalInSecs;

  @PostConstruct
  public void init() throws AdServicesException {
    if (intervalInSecs > 0) {
      log.info("Scheduling Cache refresh job with interval {} seconds", intervalInSecs);
      final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
      final CacheJob job = new CacheJob(initializer);
      service.scheduleAtFixedRate(job, getInitialDelay(), intervalInSecs, TimeUnit.SECONDS);
    } else {
      log.info("Scheduling Cache refresh job with interval {} minutes", intervalInMins);
      final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
      final CacheJob job = new CacheJob(initializer);
      service.scheduleAtFixedRate(job, getInitialDelay(), intervalInMins, TimeUnit.MINUTES);
    }
  }

  private int getInitialDelay() {
    final Calendar cal = Calendar.getInstance();
    if (intervalInSecs > 0) {
      final int minute = cal.get(Calendar.SECOND);
      final int mod = minute % intervalInSecs;
      return intervalInSecs - mod;
    }
    final int minute = cal.get(Calendar.MINUTE);
    final int mod = minute % intervalInMins;
    return intervalInMins - mod;
  }

}
