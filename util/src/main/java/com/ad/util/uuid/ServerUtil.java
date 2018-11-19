package com.ad.util.uuid;

import java.util.UUID;

/**
 * @author sagupta
 */

public class ServerUtil {

  public ServerUtil() {

    throw new AssertionError("error while initializing theclass");
  }


  /**
   * @return generate UUID.
   */
  public String generateGuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();

  }

}
