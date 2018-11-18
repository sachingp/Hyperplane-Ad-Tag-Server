package com.ad.server;

import com.ad.server.servlet.EventTrackServlet;
import com.ad.server.servlet.SyncServlet;
import com.ad.server.servlet.TagServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


/**
 * @author sagupta Main Server entry point and handles all the request with different end point.
 */

public class AdServer {

  public static void main(String[] args) throws Exception {

    Server server = new Server(80);
    ServletContextHandler handler = new ServletContextHandler(server, "/");
    handler.addServlet(SyncServlet.class, "syncTrack");
    handler.addServlet(TagServlet.class, "adTag");
    handler.addServlet(EventTrackServlet.class, "eventTrack");
    server.start();
  }
}
