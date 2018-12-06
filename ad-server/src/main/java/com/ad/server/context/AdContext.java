package com.ad.server.context;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdContext {

  String tag;
  String country;
  String deviceId;
  String userAgent;
  List<String> segments;
  Map<String, String> params;
  String sessionId;
  Integer creativeId;
  Integer partnerId;
}
