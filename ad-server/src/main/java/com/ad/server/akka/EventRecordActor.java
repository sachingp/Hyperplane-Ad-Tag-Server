package com.ad.server.akka;


import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ad.server.context.AdContext;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.constants.AdServerConstants.PARAMS;
import com.ad.util.event.Event;
import com.ad.util.json.JsonService;
import com.ad.util.logging.LoggingService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sagupta
 */

@Slf4j
public class EventRecordActor extends AbstractActor {

  public static final String ACTOR_NAME = "EVENT_RECORD_ACTOR";


  static public Props props() {
    return Props.create(EventRecordActor.class, () -> new EventRecordActor());
  }

  /**
   * push the data to logger/later Kafka
   */

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(AdContext.class, adContext -> {
      Event event = new Event();
      event.setSessionId(adContext.getSessionId());
      event.setEventId(adContext.getEventId());
      event.setDeviceId(adContext.getDeviceId());
      event.setIpAddress(adContext.getIpAddress());
      event.setCountry(adContext.getCountry());
      event.setUserAgent(adContext.getUserAgent());
      event.setCookie(adContext.getCookieId());
      event.setTag(adContext.getTag());
      event.setCreativeId(adContext.getCreativeId());
      event.setCampaignId(adContext.getCampaignId());
      event.setAdvertiserId(adContext.getAdvertiserId());
      event.setAccountId(adContext.getAccountId());
      event.setCacheBuster(adContext.getCacheBuster());
      if (adContext.getParams() != null && !adContext.getParams().isEmpty()) {

        event.setAppBundle(adContext.getParams().get(PARAMS.EXTERNAL_BUNDLE_ID.getName()));
        event.setSite(adContext.getParams().get(PARAMS.EXTERNAL_SITE_ID.getName()));
        event.setLatitude(adContext.getParams().get(PARAMS.GPS_LAT.getName()));
        event.setLongitude(adContext.getParams().get(PARAMS.GPS_LNG.getName()));
        event.setAppId(adContext.getParams().get(PARAMS.EXTERNAL_APP_ID.getName()));
        event.setAppName(adContext.getParams().get(PARAMS.EXTERNAL_APP_NAME.getName()));
        event.setExternalAdServer(adContext.getParams().get(PARAMS.EXTERNAL_AD_SERVER.getName()));
        event.setExternalPlacementId(
            adContext.getParams().get(PARAMS.EXTERNAL_PLACEMENT_ID.getName()));
        event.setExternalCreativeId(
            adContext.getParams().get(PARAMS.EXTERNAL_CREATIVE_ID.getName()));
        event.setExternalCampaignId(
            adContext.getParams().get(PARAMS.EXTERNAL_CAMPAIGN_ID.getName()));
        event.setBidId(adContext.getParams().get(PARAMS.BID_ID.getName()));
      }

      LocalDateTime time = LocalDateTime.now();
      event.setTimeStamp(new Long(time.format(
          DateTimeFormatter.ofPattern(GENERAL.SECOND_DATE_FORMAT))));
      event.setHourTimeDimension(new Long(time.format(
          DateTimeFormatter.ofPattern(GENERAL.HOUR_DATE_FORMAT))));
      event.setTagServed(adContext.isTagServed());
      // get data from AdContext
      LoggingService.logEvent(JsonService.createJson(event));
    }).build();
  }
}
