package com.ad.util.event;

import lombok.Data;

/**
 * @author sagupta
 */

@Data
public class Event {

  int eventId;
  String session_id;
  String ip_address;
  String user_agent;
  String country;
  String tag;
  String creativeId;
  String os;
  String userId;
  String userIdType;
  String site;
  String app_bundle;
  double latitude;
  double longitude;
  int campaignId;

}
