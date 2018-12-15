package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.server.context.ClickContext;
import com.ad.server.macros.ScriptMacros;
import com.ad.server.targeting.TagTargeting;
import com.ad.util.constants.AdServerConstants.PARAMS;
import com.ad.util.event.EventEnum;
import com.ad.util.geo.CountryCode;
import com.ad.util.geo.GeoLocationService;
import com.ad.util.uuid.ServerUtil;
import com.google.common.base.Strings;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author sagupta
 */

@Slf4j
public class AdHandler extends AbstractRequestHandler {

  public AdHandler(RoutingContext routingContext) {
    super(routingContext);
  }


  @Override
  public void handleRequest() {
    long startTime = System.currentTimeMillis();
    log.debug("Start Time ::{}", startTime);
    String tagGuid = this.routingContext.request().getParam("guid");
    log.debug("Tag Guid :: {}", tagGuid);
    if (!Strings.isNullOrEmpty(tagGuid)) {
      log.debug("Tag Guid : {}", tagGuid);
      String ip = getRequestIp();
      log.debug("Request Ip Address : {},", ip);
      String userAgent = getUserAgent();
      log.debug("Request User Agent : {},", userAgent);
      String country = null;
      if (ip != null) {
        try {
          String cn = GeoLocationService.getLocationForIp(ip).getCountry().getIsoCode();
          if (!Strings.isNullOrEmpty(cn)) {
            country = CountryCode.getISO3CountryCode(cn);
            log.debug("Country Code : {}", country);

          }
        } catch (Exception e) {
          log.error("Error while determining the geo location from ip :: {} , exception : {}", ip,
              e.toString());
        }
        log.debug("Country for the ip :: {}, country :: {}", ip, country);
      }

      String sessionId = ServerUtil.getUID();
      Map<String, String> params = getRequestParams();
      log.debug("Request Params : {}", params);
      String deviceId = getDeviceId(params);
      log.debug("Device Id :: {} ", deviceId);
      Cookie cookie = getCookie(deviceId);
      log.debug("Cookie Value ::{}", cookie.getValue());
      AdContext adContext = createAdContext(sessionId, ip, tagGuid, country, params, deviceId,
          userAgent, EventEnum.AdRequest.getType(), cookie.getValue());
      // check the cache buster, if not replaced set the context
      if (params != null && !params.isEmpty()) {
        log.debug("Check Cache Buster ::");
        if (!params.containsKey(PARAMS.ORD.getName())) {
          adContext.setCacheBuster(0);
        }
      } else {
        log.debug("Params empty");
      }
      TagTargeting tagTargeting = new TagTargeting(adContext);
      boolean targetingResult = tagTargeting.selection();
      log.debug("Targeting Result :: {}", targetingResult);
      if (targetingResult) {
        String scriptData = CacheService.getTagScriptData(tagGuid);
        AkkaSystem.getInstance().publishEventRecord(adContext);
        if (params.containsKey(PARAMS.CLICK_THROUGH.getName())) {
          ClickContext clickContext = new ClickContext(sessionId,
              params.get(PARAMS.CLICK_THROUGH.getName()));
          AkkaSystem.getInstance().insertSessionClick(clickContext);
        }
        // set Cookie
        setCookie(cookie);
        if (!Strings.isNullOrEmpty(scriptData)) {
          ScriptMacros scriptMacros = new ScriptMacros(scriptData, adContext);
          // record event
          this.routingContext.response().setStatusCode(200).end(scriptMacros.addMacros());
        } else {
          sendError(204, this.routingContext.response());
        }
      } else {
        sendError(204, this.routingContext.response());
      }
    } else {
      sendError(204, this.routingContext.response());
    }

    long endTime = System.currentTimeMillis();
    log.debug("End Time :: {} ", endTime);
    long time = endTime - startTime;
    log.debug("Time Take :: {} ", time);
  }
}
