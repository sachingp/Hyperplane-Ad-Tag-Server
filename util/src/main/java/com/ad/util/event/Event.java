package com.ad.util.event;

import lombok.Data;

/**
 * @author sagupta
 */

@Data
public class Event {

  int eventId;
  String ip_address;
  String user_agent;
  String country;
  String tagGuid;
  String creativeId;
  String os;
  String userId;
  String userIdType;
  String site;
  String app_bundle;
  double latitude;
  double logitude;
  int campaignId;

}
