package com.ad.server;

import com.ad.server.handlers.AdHandler;
import com.ad.server.handlers.RequestHandler;
import com.ad.util.PropertiesUtil;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.geo.GeoLocationService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 *
 * Vertx version of Ad Server.
 */

@Slf4j
public class AdServerVertx extends AbstractVerticle {

  @Override
  public void start() {

    try {
      GeoLocationService
          .init(PropertiesUtil.getProperty(GENERAL.MAXMIND_GEO_DATABASE_LOCATION_PROPERTY_NAME));
    } catch (Exception e) {
      System.exit(0);
    }
    AdServerRedisClient.getInstance();

    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/ads").handler(this::handleAdRequest);
    router.put("/events").handler(this::handleEventRequest);
    router.get("/pixels").handler(this::handlePixelRequest);

    vertx.createHttpServer().requestHandler(router).listen(8080);

  }

  private void handleAdRequest(RoutingContext routingContext) {
    RequestHandler handler = new AdHandler(routingContext);
    handler.handleRequest();
  }

  private void handleEventRequest(RoutingContext routingContext) {

  }

  private void handlePixelRequest(RoutingContext routingContext) {

  }

  /**
   * Start the Vertx server.
   */

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new AdServerVertx());
  }

}
