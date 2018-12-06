package com.ad.server.akka;


import akka.actor.AbstractActor;
import akka.actor.Props;
import com.ad.server.context.AdContext;
import com.ad.util.event.Event;
import com.ad.util.json.JsonService;
import com.ad.util.logging.LoggingService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 */

@Slf4j
public class EventRecordActor extends AbstractActor {

  public static final String ACTOR_NAME = "EVENT_RECORD_ACTOR";


  static public Props props() {
    return Props.create(EventRecordActor.class, () -> new EventRecordActor());
  }

  /**
   * push the data to logger/later Kafka
   */

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(AdContext.class, record -> {
      Event event = new Event();
      // get data from AdContext
      LoggingService.logEvent(JsonService.createJson(event));
    }).build();
  }
}
