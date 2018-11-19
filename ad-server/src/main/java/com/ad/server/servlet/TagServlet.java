package com.ad.server.servlet;

import com.ad.server.servlet.util.DeviceData;
import com.ad.server.servlet.util.GeoData;
import com.ad.server.servlet.util.ServletUtil;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import java.util.Map;
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

    AdServerRedisClient.init();

  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getMethod().equalsIgnoreCase(AdServerConstants.Genral.SUPPORTED_HTTP_REQUEST)) {
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

    if (StringUtils.isEmpty(tagId)) {

      String ip = ServletUtil.getRequestIp(req);
      String userAgent = ServletUtil.getUserAgent(req);
      String country = null;
      if (ip != null) {

        country = GeoData.getCountry(ip);
        log.debug("Country for the ip :: {}, country :: {}", ip, country);

      }

      Map<String, String> deviceMap = null;

      if (userAgent != null) {
        deviceMap = DeviceData.getDeviceMap(userAgent);
      }
      resp.setStatus(HttpStatus.OK_200);

      resp.getWriter().println("<script></script>");
    } else {
      log.warn("Invalid Tag Id for the request :: {} ", tagId);
      resp.setStatus(HttpStatus.NO_CONTENT_204);
    }

  }
}
