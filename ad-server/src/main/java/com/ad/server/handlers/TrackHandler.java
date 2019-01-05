package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.util.event.EventUtil;
import com.google.common.base.Strings;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author sagupta
 */

@Slf4j
public class TrackHandler extends AbstractRequestHandler {

  public TrackHandler(RoutingContext routingContext) {
    super(routingContext);
  }

  @Override
  public void handleRequest() {
    String event = this.routingContext.request().getParam("eventId");
    String sessionId = this.routingContext.request().getParam("sessionId");
    String tagGuid = this.routingContext.request().getParam("guid");
    if (!Strings.isNullOrEmpty(event) && !Strings.isNullOrEmpty(sessionId) && !Strings
        .isNullOrEmpty(tagGuid)) {
      Integer eventId = 0;
      try {
        eventId = Integer.parseInt(event);
        log.debug("Event Id :: {}", eventId);
        if (EventUtil.getEvents().contains(eventId)) {

          String ip = getRequestIp();
          log.debug("Request Ip Address : {},", ip);
          String userAgent = getUserAgent();
          log.debug("Request User Agent : {},", userAgent);
          String country = getCountry(ip);
          Map<String, String> params = getRequestParams();
          log.debug("Request Params : {}", params);
          String deviceId = getDeviceId(params);
          log.debug("Device Id :: {} ", deviceId);
          Cookie cookie = getCookie(deviceId);
          log.debug("Cookie Value ::{}", cookie.getValue());
          AdContext adContext = createAdContext(sessionId, ip, tagGuid, country, params, deviceId,
              userAgent, eventId, cookie.getValue());

          CacheService.setTagDetails(adContext);
          // set Cookie
          setCookie(cookie);
          this.routingContext.response().setStatusCode(200).end();
          AkkaSystem.getInstance().publishEventRecord(adContext);

        } else {
          log.error("Unknown Event::");
          sendError(204, routingContext.response());
        }


      } catch (Exception e) {
        log.error("Error while converting the event id to integer");
        sendError(204, routingContext.response());
      }


    } else {
      log.error("No tag guid or event Id or session Id passed ::");
      sendError(204, routingContext.response());
    }
  }
}
