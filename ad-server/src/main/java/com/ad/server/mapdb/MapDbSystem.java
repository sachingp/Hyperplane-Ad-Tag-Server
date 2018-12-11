package com.ad.server.mapdb;

import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * @author sagupta
 */

@Slf4j

public class MapDbSystem {

  private static MapDbSystem mapDbSystem = null;

  final DB db;

  private MapDbSystem() {
    log.info("Initialize the MapDb");
    db = DBMaker.memoryDB().make();

  }

  public DB getDb() {

    return this.db;

  }

  /**
   *
   * @return
   */

  public static MapDbSystem getInstance() {
    if (mapDbSystem == null) {
      synchronized (MapDbSystem.class) {
        if (mapDbSystem == null) {
          mapDbSystem = new MapDbSystem();
        }
      }
    }
    return mapDbSystem;
  }

}
