package com.ad.server.handlers;

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
      Cookie cookie = getCookie(deviceId);
      setCookie(cookie);
      logData(null);

      this.routingContext.response().end();
    } else {
      sendError(204, this.routingContext.response());
    }
  }
}
