package com.ad.server.handlers;

import com.ad.server.context.AdContext;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Cookie;

import java.util.Map;

/**
 * @author sagupta
 */

public interface RequestHandler {

  public String getCountry(String ip);

  public void handleRequest();

  public void logData(final String logEvent);

  public Cookie getCookie(final String id);

  public void setCookie(final Cookie cookie);

  public Map<String, String> getRequestParams();

  public String getRequestIp();

  public void sendError(final int statusCode, final HttpServerResponse response);

  public String getUserAgent();

  public String getDeviceId(final Map<String, String> params);

  public AdContext createAdContext(final String sessionId, final String ipAddress,
      final String tagGuid,
      final String country,
      final Map<String, String> params, final String deviceId, final String userAgent,
      final int eventType, final String cookieId);

}
