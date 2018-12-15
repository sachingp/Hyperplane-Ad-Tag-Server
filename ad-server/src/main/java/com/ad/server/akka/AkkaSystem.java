package com.ad.server.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.ad.server.context.AdContext;
import com.ad.server.context.ClickContext;
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
  private final List<ActorRef> clickRedisActors = new LinkedList<ActorRef>();
  private ActorSystem actorSystem;
  private String akkaConfigFile;
  private int actorCount = 100000;

  private AkkaSystem() {

    init();
    createEventRecordActors();
    createClickRedisActors();
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
    try {
      this.actorSystem = ActorSystem.create(akkaSystemName,
          ConfigFactory.load());
    } catch (Exception e) {

      log.error("Error in initializing akka system :: {}", e);

    }
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

  private void createClickRedisActors() {
    log.debug("Create click redis actors. Total actors:{}",
        actorCount);
    ActorRef actor;
    final List<ActorRef> actors = new LinkedList<ActorRef>();
    for (int i = 0; i < actorCount; i++) {
      actor = actorSystem.actorOf(EventRecordActor.props(),
          ClickRedisActor.ACTOR_NAME + i);
      actors.add(actor);
    }
    clickRedisActors.addAll(actors);

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

  private ActorRef getClickRedisActor() {
    log.debug("Getting Click redis actor from Pool");
    return clickRedisActors.get(randomActorId(0, clickRedisActors.size()));
  }

  /**
   *
   * @param adContext
   */

  public void publishEventRecord(final AdContext adContext) {
    getEventRecordActor().tell(adContext, ActorRef.noSender());

  }


  /**
   *
   * @param clickContext
   */

  public void insertSessionClick(final ClickContext clickContext) {
    getClickRedisActor().tell(clickContext, ActorRef.noSender());

  }

  public static void main(String[] args) {
    AkkaSystem.getInstance();
  }
}
