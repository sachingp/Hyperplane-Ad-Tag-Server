package com.ad.server;

import com.ad.server.servlet.EventTrackServlet;
import com.ad.server.servlet.SyncServlet;
import com.ad.server.servlet.TagServlet;
import com.ad.util.PropertiesUtil;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.geo.GeoLocationService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


/**
 * @author sagupta Main Server entry point and handles all the request with different end point.
 */

@Slf4j
public class AdServer {

  public static void main(String[] args) throws Exception {

    Server server = new Server(80);
    ServletContextHandler handler = new ServletContextHandler(server, "/");
    handler.addServlet(SyncServlet.class, "sync?");
    handler.addServlet(TagServlet.class, "ads?");
    handler.addServlet(EventTrackServlet.class, "events?");
    GeoLocationService
        .init(PropertiesUtil.getProperty(GENERAL.MAXMIND_GEO_DATABASE_LOCATION_PROPERTY_NAME));
    AdServerRedisClient.getInstance();
    server.start();
  }
}
