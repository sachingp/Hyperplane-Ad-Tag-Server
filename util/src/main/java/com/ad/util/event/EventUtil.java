package com.ad.util.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sagupta
 */

public class EventUtil {

  private static List<Integer> events = null;

  static {

    eventLoader();

  }

  public EventUtil() {
    throw new AssertionError("Error while initializing the class");
  }

  private static void eventLoader() {

    events = new ArrayList<>();

    Arrays.asList(EventEnum.values()).forEach(e -> {
      events.add(e.getType());
    });

  }

  public static List<Integer> getEvents() {

    return events;

  }

}
