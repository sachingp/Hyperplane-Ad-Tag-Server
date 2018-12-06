package com.ad.server.handlers;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.CacheService;
import com.ad.server.context.AdContext;
import com.ad.server.macros.ScriptMacros;
import com.ad.server.targeting.TagTargeting;
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
              e);
        }
        log.debug("Country for the ip :: {}, country :: {}", ip, country);
      }

      String sessionId = ServerUtil.getUID();
      Map<String, String> params = getRequestParams();
      log.info("Request Params : {}", params);
      String deviceId = getDeviceId(params);
      log.info("Device Id :: {} ", deviceId);

      AdContext adContext = createAdContext(sessionId, tagGuid, country, params, deviceId,
          userAgent);

      TagTargeting tagTargeting = new TagTargeting(adContext);
      boolean result = tagTargeting.selection();
      if (result) {

        String scriptData = CacheService.getTagScriptData(tagGuid);
        if (Strings.isNullOrEmpty(scriptData)) {
          ScriptMacros scriptMacros = new ScriptMacros(scriptData, adContext);
          Cookie cookie = getCookie(deviceId);
          // Check id
          setCookie(cookie);
          // record event
          AkkaSystem.getInstance().publishEventRecord(adContext);
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

  /**
   * @return create ad context.
   */

  private AdContext createAdContext(String sessionId, String tagGuid, String country,
      Map<String, String> params, String deviceId, String userAgent) {
    AdContext adContext = new AdContext();
    adContext.setSessionId(sessionId);
    adContext.setTag(tagGuid);
    adContext.setCountry(country);
    adContext.setParams(params);
    adContext.setDeviceId(deviceId);
    adContext.setUserAgent(userAgent);

    return adContext;

  }

}
