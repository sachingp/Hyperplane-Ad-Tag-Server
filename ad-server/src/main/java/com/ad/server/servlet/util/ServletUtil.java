package com.ad.server.servlet.util;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sagupta
 */

@Slf4j
public class ServletUtil {

  private static final String WITH_CREDENTIALS = "withCredentials";

  private ServletUtil() {
    // Prevent instantiation
    throw new AssertionError();
  }

  /**
   * urlencode input string.
   *
   * @param input {@link String} urlencoded string
   * @return {@link String} urldecoded string
   * @throws UnsupportedEncodingException when encoding scheme is not valid
   */
  public static String urlDecode(final String input) throws UnsupportedEncodingException {
    return (StringUtils.isBlank(input)) ? input
        : URLDecoder.decode(input, StandardCharsets.UTF_8.toString());
  }

  /**
   * urldecode input string.
   *
   * @param input {@link String} string to encode
   * @return {@link String} urlencoded string
   * @throws UnsupportedEncodingException when encoding scheme is not valid
   */
  public static String urlEncode(final String input) throws UnsupportedEncodingException {
    return (StringUtils.isBlank(input)) ? input
        : URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
  }

  /**
   * Write content to the response.
   *
   * @param response {@code HttpServletResponse} response object
   * @param content {@code String} content to write
   */
  static void flushContent(final HttpServletResponse response, final String content) {
    response.setContentLength(content.getBytes().length);
    try {
      PrintWriter writer = response.getWriter();
      writer.write(content);
      if (!response.isCommitted()) {
        writer.flush();
      }
    } catch (Exception ex) {
      log.warn("Failed to flush content", ex);
    }
  }

  /**
   * @param errorCode return empty response with required error code.
   */

  static void getErrorResponse(final HttpServletRequest request, final HttpServletResponse response,
      int errorCode) {
    if (response.isCommitted()) {
      return;
    }

    updateHeader(request, response);
    response.setContentType("text/xml");
    response.setStatus(errorCode);
    flushContent(response, "");
  }


  /**
   * @return get ip address from paramenet, header or ip packet.
   */
  public static String getRequestIp(final HttpServletRequest request) {

    String ip = Optional.ofNullable(request.getParameter("ip"))
        .orElse(request.getHeader("X-FORWARDED-FOR"));

    if (ip == null) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }

  /**
   * @return get user agent for the requested device.
   */

  public static String getUserAgent(final HttpServletRequest request) {

    String userAgent = Optional.ofNullable(request.getParameter("user_agent"))
        .orElse(request.getHeader("USER-AGENT"));

    return userAgent;
  }

  /**
   * @return get user agent for the requested device.
   */

  public static String getTagId(final HttpServletRequest request) {

    String tagGuid = Optional.ofNullable(request.getParameter("guid"))
        .orElse(null);

    return tagGuid;
  }

  public static Map<String, String[]> getRequestParams(final HttpServletRequest request) {
    return Optional.ofNullable(request.getParameterMap()).orElse(null);
  }


  /**
   * + .
   *
   * @param response HttpServletResponse
   * @return HttpServletResponse
   */
  static void getTimeOutResponse(final HttpServletRequest request,
      final HttpServletResponse response) {
    if (response.isCommitted()) {
      return;
    }

    updateHeader(request, response);
    response.setContentType("text/xml");
    response.setStatus(HttpServletResponse.SC_OK);
    flushContent(response, "");
  }

  static boolean getCredentialFlagFromHeader(final HttpServletRequest request) {

    return Optional.ofNullable(request.getHeader(WITH_CREDENTIALS))
        .filter(header -> !header.isEmpty())
        .map(nonEmptyHeader -> {
          boolean result;
          try {
            result = Boolean.parseBoolean(nonEmptyHeader);
          } catch (Exception ex) {
            result = false;
          }

          return result;
        }).orElse(false);

  }

  /**
   * + get domain from request.
   */
  static String getDomainFromOriginHeader(final HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(HttpHeaders.ORIGIN)).orElse("");
  }


  static void updateHeader(final HttpServletRequest request, final HttpServletResponse response) {
    boolean isWithCredential = getCredentialFlagFromHeader(request);
    String domainNameInOrigin = getDomainFromOriginHeader(request);

    if (isWithCredential && !StringUtils.isEmpty(domainNameInOrigin)) {
      response
          .addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, getDomainFromOriginHeader(request));
      response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    } else {
      response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }
  }

}
