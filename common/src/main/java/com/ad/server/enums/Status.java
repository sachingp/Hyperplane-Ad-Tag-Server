package com.ad.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum Status {
  ACTIVE(1), PAUSED(2), ARCHIVED(3);
	
  @Getter
  private final int id;

  private static final Map<Integer, Status> LOOKUP_MAP = new HashMap<Integer, Status>();

  static {
    Arrays.stream(Status.values()).forEach(item -> LOOKUP_MAP.put(item.getId(), item));
  }

  public static Status getById(Integer id) {
    return LOOKUP_MAP.get(id);
  }

}
