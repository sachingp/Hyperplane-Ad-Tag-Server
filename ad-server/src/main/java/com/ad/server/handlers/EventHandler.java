package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.context.AdContext;
import com.ad.util.event.EventUtil;
import com.ad.util.geo.GeoLocationService;
import com.google.common.base.Strings;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author sagupta
 */

@Slf4j
public class EventHandler extends AbstractRequestHandler {

  public EventHandler(RoutingContext routingContext) {
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
          String country = null;
          if (ip != null) {
            try {
              country = GeoLocationService.getLocationForIp(ip).getCountry().getIsoCode();
            } catch (Exception e) {
              log.error("Error while determining the geo location from ip :: {} , exception", ip,
                  e);
            }
            log.debug("Country for the ip :: {}, country :: {}", ip, country);
          }

          Map<String, String> params = getRequestParams();
          log.debug("Request Params : {}", params);
          String deviceId = getDeviceId(params);
          log.debug("Device Id :: {} ", deviceId);
          Cookie cookie = getCookie(deviceId);
          log.debug("Cookie Value ::{}", cookie.getValue());
          AdContext adContext = createAdContext(sessionId, ip, tagGuid, country, params, deviceId,
              userAgent, eventId, cookie.getValue());

          AkkaSystem.getInstance().publishEventRecord(adContext);
          // set Cookie
          setCookie(cookie);
          this.routingContext.response().setStatusCode(200).end();

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
