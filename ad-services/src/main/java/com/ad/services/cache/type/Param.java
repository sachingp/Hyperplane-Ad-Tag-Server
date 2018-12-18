package com.ad.services.cache.type;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Param {

  private final String id;
  private final String display;

  public Param(final String combination) {
    final String[] tokens = combination.split(":");
    this.id = tokens[0];
    this.display = tokens[1];
  }

}
