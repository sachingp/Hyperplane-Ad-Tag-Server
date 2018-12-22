package com.ad.server.context;


import com.ad.server.pojo.CreativeAssets;
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
  int creativeId = 0;
  int partnerId;
  int eventId;
  String cookieId;
  String ipAddress;
  int cacheBuster = 1;
  int campaignId;
  int advertiserId;
  int accountId;
  boolean tagServed = true;
  List<CreativeAssets> creativeAssets;
}
