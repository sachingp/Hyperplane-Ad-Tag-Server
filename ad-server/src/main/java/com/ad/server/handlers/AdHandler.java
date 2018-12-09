package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.server.macros.ScriptMacros;
import com.ad.server.targeting.TagTargeting;
import com.ad.util.constants.AdServerConstants.PARAMS;
import com.ad.util.event.EventEnum;
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
    String tagGuid = this.routingContext.request().getParam("guid");
    log.info("Tag Guid :: {}", tagGuid);
    if (!Strings.isNullOrEmpty(tagGuid)) {
      log.info("Tag Guid : {}", tagGuid);
      String ip = getRequestIp();
      log.info("Request Ip Address : {},", ip);
      String userAgent = getUserAgent();
      log.info("Request User Agent : {},", userAgent);
      String country = null;
      if (ip != null) {
        try {
          country = GeoLocationService.getLocationForIp(ip).getCountry().getIsoCode();
        } catch (Exception e) {
          log.error("Error while determining the geo location from ip :: {} , exception", ip,
              e.toString());
        }
        log.debug("Country for the ip :: {}, country :: {}", ip, country);
      }

      String sessionId = ServerUtil.getUID();
      Map<String, String> params = getRequestParams();
      log.info("Request Params : {}", params);
      String deviceId = getDeviceId(params);
      log.info("Device Id :: {} ", deviceId);
      Cookie cookie = getCookie(deviceId);
      log.info("Cookie Value ::{}", cookie.getValue());
      AdContext adContext = createAdContext(sessionId, ip, tagGuid, country, params, deviceId,
          userAgent, EventEnum.AdRequest.getType(), cookie.getValue());
      // check the cache buster, if not replaced set the context
      if (params != null && !params.isEmpty()) {
        log.info("Check Cache Buster ::");
        if (!params.containsKey(PARAMS.ORD.getName())) {
          adContext.setCacheBuster(0);
        }
      } else {
        log.info("Params empty");
      }
      TagTargeting tagTargeting = new TagTargeting(adContext);
      boolean targetingResult = tagTargeting.selection();
      if (targetingResult) {
        String scriptData = CacheService.getTagScriptData(tagGuid);
        if (Strings.isNullOrEmpty(scriptData)) {
          ScriptMacros scriptMacros = new ScriptMacros(scriptData, adContext);
          // record event
          AkkaSystem.getInstance().publishEventRecord(adContext);
          // set Cookie
          setCookie(cookie);
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
  }
}
