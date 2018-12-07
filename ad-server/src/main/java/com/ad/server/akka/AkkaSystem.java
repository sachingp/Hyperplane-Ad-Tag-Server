package com.ad.server.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ad.server.context.AdContext;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sagupta
 */


@Slf4j
public class AkkaSystem {

  private static final String akkaSystemName = "AKKA_SYSTEM";
  private static volatile AkkaSystem akkaSystem = null;
  private final List<ActorRef> eventRecordActors = new LinkedList<ActorRef>();
  private ActorSystem actorSystem;
  private String akkaConfigFile;
  private int actorCount = 100000;

  private AkkaSystem() {

    init();
    createEventRecordActors();

  }

  /**
   * @return instance of Akka System
   */
  public static AkkaSystem getInstance() {
    if (akkaSystem == null) {
      synchronized (AkkaSystem.class) {
        if (akkaSystem == null) {
          akkaSystem = new AkkaSystem();
        }
      }
    }
    return akkaSystem;
  }

  private void init() {
    this.actorSystem = ActorSystem.create(akkaSystemName,
        ConfigFactory.load());
  }

  private void createEventRecordActors() {
    log.debug("Create event record actors. Total actors:{}",
        actorCount);
    ActorRef actor;
    final List<ActorRef> actors = new LinkedList<ActorRef>();
    for (int i = 0; i < actorCount; i++) {
      actor = actorSystem.actorOf(EventRecordActor.props(),
          EventRecordActor.ACTOR_NAME + i);
      actors.add(actor);
    }
    eventRecordActors.addAll(actors);

  }

  /**
   * @return actor system
   */

  public ActorSystem getActorRef() {
    return this.actorSystem;
  }

  /**
   * @return position of actor to fetch
   */

  private int randomActorId(final int min, final int max) {
    int partition = min + (int) (Math.random() * ((max - min)));
    return partition;
  }

  private ActorRef getEventRecordActor() {
    log.debug("Getting Data record actor from Pool");
    return eventRecordActors.get(randomActorId(0, eventRecordActors.size()));
  }

  /**
   *
   * @param adContext
   */

  public void publishEventRecord(final AdContext adContext) {
    getEventRecordActor().tell(adContext, ActorRef.noSender());

  }
}
