package com.ad.util.client;

import com.ad.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;


/**
 * @author sagupta
 *
 * Redis Client for Ad Server.
 */

@Slf4j
public class AdServerRedisClient {

  public static AdServerRedisClient adServerRedisClient = null;

  private static final String REDIS_HOST_PROPERTY = "jedis.connection.host";

  private static final String REDIS_PORT_PROPERTY = "jedis.connection.port";

  private static JedisPool jedisPool = null;

  private AdServerRedisClient() {

    log.info("Redis Pool Init() :: {}   : Port :: {}",
        PropertiesUtil.getProperty(REDIS_HOST_PROPERTY),
        PropertiesUtil.getProperty(REDIS_PORT_PROPERTY));
    jedisPool = new JedisPool(PropertiesUtil.getProperty(REDIS_HOST_PROPERTY),
        Integer.parseInt(PropertiesUtil.getProperty(REDIS_PORT_PROPERTY)));

  }

  public static AdServerRedisClient getInstance() {
    if (adServerRedisClient == null) {
      synchronized (AdServerRedisClient.class) {
        if (adServerRedisClient == null) {
          adServerRedisClient = new AdServerRedisClient();
        }
      }
    }

    return adServerRedisClient;

  }

  /**
   * put the data with required TTL.
   */

  public void put(String key, String value, int seconds) {
    jedisPool.getResource().set(key, value);
    if (seconds > 0) {
      jedisPool.getResource().expire(key, seconds);
    }
  }

  public String get(String key) {

    return jedisPool.getResource().get(key);
  }

}
