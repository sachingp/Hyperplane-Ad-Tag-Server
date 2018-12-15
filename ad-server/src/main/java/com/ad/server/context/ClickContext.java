package com.ad.server.context;

import lombok.Data;

/**
 * @author sagupta
 */

@Data
public class ClickContext {

  String sessionId;
  String clickURL;

  public ClickContext(String sessionId, String clickURL) {
    this.sessionId = sessionId;
    this.clickURL = clickURL;
  }
}
