package com.ad.services.cache.type;

import lombok.Data;

@Data
public class Param {

  private final String id;
  private final String display;

  public Param(final String combination) {
    final String[] tokens = combination.split(":");
    this.id = tokens[0];
    this.display = tokens[1];
  }

  public Param(final String id, final String display) {
    this.id = id;
    this.display = display;
  }

}
