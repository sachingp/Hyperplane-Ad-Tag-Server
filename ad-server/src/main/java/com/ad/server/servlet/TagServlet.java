package com.ad.server.servlet;

import com.ad.server.cache.CacheService;
import com.ad.server.servlet.util.ServletUtil;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.geo.GeoLocationService;
import com.ad.util.uuid.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sagupta
 */

@Slf4j
public class TagServlet extends HttpServlet {

  /**
   * + .
   *
   * @throws ServletException on error
   */
  @Override
  public void init() throws ServletException {
    log.debug("Tag Servlet  init");
    super.init();
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equalsIgnoreCase(GENERAL.SUPPORTED_HTTP_REQUEST)) {
      doGet(req, resp);
    } else {
      log.warn("Invalid request protocol : request : {}, response : {}", req, resp);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String tagId = ServletUtil.getTagId(req);
    log.debug("Tag Id for the request :: {} ", tagId);
    if (StringUtils.isEmpty(tagId) && validate(tagId)) {
      String ip = ServletUtil.getRequestIp(req);
      String userAgent = ServletUtil.getUserAgent(req);
      String country = null;
      if (ip != null) {
        try {
          country = GeoLocationService.getLocationForIp(ip).getCountry().getIsoCode();
        } catch (Exception e) {
          log.error("Error while determining the geo location from ip :: {} , exception", ip, e);
        }
        log.debug("Country for the ip :: {}, country :: {}", ip, country);
      }

      if (userAgent != null) {
      }

      String sessionId = ServerUtil.getUID();
      // Cookie
      // Request Params
      // Create event object
      // Targeting
      // CAll Redis
      // return response and set cookie
      // log event
      resp.setStatus(HttpStatus.OK_200);

      resp.getWriter().println("<script></script>");
    } else {
      log.warn("Invalid Tag Id for the request :: {} ", tagId);
      resp.setStatus(HttpStatus.NO_CONTENT_204);
    }
  }

  /**
   * @return validate the tag guid.
   */

  private boolean validate(String tagGuid) {
    return CacheService.isTagActive(tagGuid);

  }
}
