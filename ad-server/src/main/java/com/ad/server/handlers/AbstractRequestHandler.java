package com.ad.server.handlers;

import com.ad.server.context.AdContext;
import com.ad.util.constants.AdServerConstants;
import com.ad.util.constants.AdServerConstants.DEVICE_MACROS_GROUP;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.constants.AdServerConstants.PARAMS;
import com.ad.util.logging.LoggingService;
import com.ad.util.uuid.ServerUtil;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sagupta
 */

@Slf4j
public abstract class AbstractRequestHandler implements RequestHandler {

  final RoutingContext routingContext;

  public AbstractRequestHandler(final RoutingContext routingContext) {
    this.routingContext = routingContext;
  }

  @Override
  public void logData(final String logEvent) {

    LoggingService.logEvent(logEvent);

  }

  @Override
  public Cookie getCookie(final String id) {

    Cookie cookie = this.routingContext.getCookie(GENERAL.COOKIE_NAME);
    if (cookie == null) {
      cookie = Cookie.cookie(GENERAL.COOKIE_NAME, ServerUtil.generateAudienceId(id));
      cookie.setMaxAge(GENERAL.COOKIE_MAX_AGE);
    }
    return cookie;
  }

  @Override
  public void setCookie(final Cookie cookie) {
    this.routingContext.addCookie(cookie);
  }

  @Override
  public Map<String, String> getRequestParams() {

    Map<String, String> params = new ConcurrentHashMap<>();

    Arrays.asList(AdServerConstants.PARAMS.values()).parallelStream().forEach(e -> {

      String value = routingContext.request().getParam(e.getName());

      log.info("Param Name : {}  :: Value : {}", e.getName(), value);

      if (value != null && !value.equals(e.getMacro())) {
        params.put(e.getName(), value);
      }
    });

    return params;
  }

  @Override
  public String getRequestIp() {

    String ip = Optional.ofNullable(this.routingContext.request().getParam("ip"))
        .orElse(this.routingContext.request().getHeader("X-FORWARDED-FOR"));

    if (ip == null) {
      ip = routingContext.request().remoteAddress().host();
    }
    return ip;
  }

  @Override
  public void sendError(final int statusCode, final HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }

  @Override
  public String getUserAgent() {
    String userAgent = Optional.ofNullable(this.routingContext.request().getHeader("USER-AGENT"))
        .orElse(this.routingContext.request().getParam("user_agent"));

    return userAgent;
  }

  @Override
  public String getDeviceId(final Map<String, String> params) {
    String deviceId = null;
    for (PARAMS macros : DEVICE_MACROS_GROUP.DEVICES) {
      String value = params.get(macros.getName());
      if (value != null) {
        deviceId = value;
        return deviceId;
      }
    }
    return deviceId;
  }

  @Override
  /**
   * @return create ad context.
   */

  public AdContext createAdContext(final String sessionId, final String ipAddress,
      final String tagGuid,
      final String country,
      final Map<String, String> params, final String deviceId, final String userAgent,
      final int eventType, final String cookieId) {
    AdContext adContext = new AdContext();
    adContext.setSessionId(sessionId);
    adContext.setTag(tagGuid);
    adContext.setCountry(country);
    adContext.setParams(params);
    adContext.setDeviceId(deviceId);
    adContext.setUserAgent(userAgent);
    adContext.setEventId(eventType);
    adContext.setCookieId(cookieId);
    adContext.setIpAddress(ipAddress);

    return adContext;

  }
}
