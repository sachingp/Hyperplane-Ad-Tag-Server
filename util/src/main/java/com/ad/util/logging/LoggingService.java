package com.ad.util.logging;


import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 */

@Slf4j(topic = "HOUR_LOGGER")
public class LoggingService {

  public LoggingService() {
    throw new AssertionError("Error while initializing the class");
  }

  public static void logEvent(String eventJson) {
    log.info(eventJson);
  }

}
