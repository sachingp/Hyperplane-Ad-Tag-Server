package com.ad.server;

import com.ad.server.akka.AkkaSystem;
import com.ad.server.cache.AdServerCache;
import com.ad.server.handlers.AdHandler;
import com.ad.server.handlers.ClickHandler;
import com.ad.server.handlers.EventHandler;
import com.ad.server.handlers.RequestHandler;
import com.ad.server.mapdb.MapDbSystem;
import com.ad.util.PropertiesUtil;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.GENERAL;
import com.ad.util.geo.GeoLocationService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
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

  /**
   * Start the Vertx server.
   */

  public static void main(String[] args) {
    VertxOptions options = new VertxOptions();
    options.setBlockedThreadCheckInterval(1000 * 60 * 60);
    Vertx vertx = Vertx.vertx(options);
    vertx.deployVerticle(new AdServerVertx());
  }

  @Override
  public void start() {

    try {
      GeoLocationService
          .init(PropertiesUtil.getProperty(GENERAL.MAXMIND_GEO_DATABASE_LOCATION_PROPERTY_NAME));
    } catch (Exception e) {
      System.exit(0);
    }
    AdServerRedisClient.getInstance();
    AdServerCache.getInstance();
    MapDbSystem.getInstance();
    AkkaSystem.getInstance();
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/ads").handler(this::handleAdRequest);
    router.put("/events").handler(this::handleEventRequest);
    router.get("/clk").handler(this::handleClickRequest);

    vertx.createHttpServer().requestHandler(router).listen(8080);

  }

  /**
   * handle ad requests.
   */

  private void handleAdRequest(final RoutingContext routingContext) {
    RequestHandler handler = new AdHandler(routingContext);
    handler.handleRequest();
  }

  /**
   * handle event requests.
   */

  private void handleEventRequest(final RoutingContext routingContext) {

    RequestHandler handler = new EventHandler(routingContext);
    handler.handleRequest();

  }

  /**
   * handle click events.
   */

  private void handleClickRequest(final RoutingContext routingContext) {
    RequestHandler handler = new ClickHandler(routingContext);
    handler.handleRequest();

  }

  @Override
  public void stop() throws Exception {

    AdServerRedisClient.getInstance().closePool();
    MapDbSystem.getInstance().getDb().close();
    log.info("Server Stopped !!!");
  }
}
