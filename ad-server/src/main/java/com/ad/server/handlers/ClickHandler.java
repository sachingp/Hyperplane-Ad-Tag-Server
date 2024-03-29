package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.util.event.EventEnum;
import com.google.common.base.Strings;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ClickHandler extends AbstractRequestHandler {

  public ClickHandler(RoutingContext routingContext) {
    super(routingContext);
  }

  @Override
  public void handleRequest() {
    String sessionId = this.routingContext.request().getParam("sessionId");
    String tagGuid = this.routingContext.request().getParam("guid");
    if (!Strings.isNullOrEmpty(sessionId) && !Strings
        .isNullOrEmpty(tagGuid)) {
      Integer eventId = 0;
      try {
        eventId = EventEnum.Click.getType();
        log.debug("Event Id :: {}", eventId);
        String ip = getRequestIp();
        log.debug("Request Ip Address : {},", ip);
        String userAgent = getUserAgent();
        log.debug("Request User Agent : {},", userAgent);
        String country = getCountry(ip);
        Map<String, String> params = getRequestParams();
        log.info("Request Params : {}", params);
        String deviceId = getDeviceId(params);
        log.debug("Device Id :: {} ", deviceId);
        Cookie cookie = getCookie(deviceId);
        log.debug("Cookie Value ::{}", cookie.getValue());
        AdContext adContext = createAdContext(sessionId, ip, tagGuid, country, params, deviceId,
            userAgent, eventId, cookie.getValue());
        CacheService.setTagDetails(adContext);
        // set Cookie
        setCookie(cookie);
        String clickURL = CacheService.getClickThroughURL(sessionId, adContext);
        log.debug("Clickthrough URL :: {}", clickURL);

        // redirect to click through URL
        if (!Strings.isNullOrEmpty(clickURL)) {
          this.routingContext.response().putHeader("location", clickURL).setStatusCode(302).end();
        } else {
          adContext.setTagServed(false);
          log.error("Empty click URL");
          sendError(204, routingContext.response());
        }

        AkkaSystem.getInstance().publishEventRecord(adContext);


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

