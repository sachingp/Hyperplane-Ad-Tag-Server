package com.ad.server.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ad.server.context.ClickContext;
import com.ad.server.macros.LocalCache;
import com.ad.util.client.AdServerRedisClient;
import com.ad.util.constants.AdServerConstants.GENERAL;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * @author sagupta
 */

@Slf4j
public class ClickRedisActor extends AbstractActor {

  public static final String ACTOR_NAME = "CLICK_REDIS_ACTOR";


  static public Props props() {
    return Props.create(ClickRedisActor.class, () -> new ClickRedisActor());
  }


  @Override
  public Receive createReceive() {
    return receiveBuilder().match(ClickContext.class, clickContext -> {
      log.debug("Adding ClickThrough URL for session Id :: {}, click URL :: {} ",
          clickContext.getSessionId(), clickContext.getClickURL());
      if (isValidURL(clickContext.getClickURL())) {
        LocalCache.getInstance().put(clickContext.getSessionId(), clickContext.getClickURL());
        AdServerRedisClient.getInstance()
            .put(clickContext.getSessionId(), clickContext.getClickURL(),
                GENERAL.REDIS_TTL_SESSSION);
      }
    }).build();
  }

  /**
   * @return if the URL is valid
   */

  private boolean isValidURL(String url) {
    URL u = null;
    try {
      u = new URL(url);
    } catch (MalformedURLException e) {
      return false;
    }

    try {
      u.toURI();
    } catch (URISyntaxException e) {
      return false;
    }
    return true;
  }

}
