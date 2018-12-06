package com.ad.server.handlers;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Cookie;

import java.util.Map;

/**
 * @author sagupta
 */

public interface RequestHandler {

  public void handleRequest();

  public void logData(String logEvent);

  public Cookie getCookie(String id);

  public void setCookie(Cookie cookie);

  public Map<String, String> getRequestParams();

  public String getRequestIp();

  public void sendError(int statusCode, HttpServerResponse response);

  public String getUserAgent();

  public String getDeviceId(Map<String, String> params);

}
