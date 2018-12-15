package com.ad.util.event;

import lombok.Data;

/**
 * @author sagupta
 */

@Data
public class Event {

  int eventId;
  String sessionId;
  String ipAddress;
  String userAgent;
  String country;
  String tag;
  Integer creativeId;
  Integer campaignId;
  Integer advertiserId;
  Integer accountId;
  String os;
  String deviceId;
  String site;
  String appBundle;
  String appId;
  String appName;
  String latitude;
  String longitude;
  String cookie;
  String externalCampaignId;
  String externalPlacementId;
  String externalCreativeId;
  String externalAdServer;
  int cacheBuster = 1;
  long timeStamp;
  long hourTimeDimension;
}
