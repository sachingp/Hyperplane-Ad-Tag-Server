package com.ad.util.client;

import com.ad.util.PropertiesUtil;
import redis.clients.jedis.JedisPool;

/**
 * @author sagupta
 *
 * Redis Client for Ad Server.
 */


public class AdServerRedisClient {

  public static AdServerRedisClient adServerRedisClient = null;

  private static final String REDIS_HOST_PROPERTY = "jedis.connection.host";

  private static final String REDIS_PORT_PROPERTY = "jedis.connection.port";

  private static JedisPool jedisPool = null;

  private AdServerRedisClient() {

    jedisPool = new JedisPool(PropertiesUtil.getProperty(REDIS_HOST_PROPERTY),
        Integer.parseInt(PropertiesUtil.getProperty(REDIS_PORT_PROPERTY)));

  }

  public static AdServerRedisClient init() {

    if (adServerRedisClient == null) {
      synchronized (AdServerRedisClient.class) {
        if (adServerRedisClient == null) {
          adServerRedisClient = new AdServerRedisClient();
        }
      }
    }

    return adServerRedisClient;

  }

  public void put(String key, String value) {
    jedisPool.getResource().set(key, value);
  }

  public String get(String key) {

    return jedisPool.getResource().get(key);
  }

}
