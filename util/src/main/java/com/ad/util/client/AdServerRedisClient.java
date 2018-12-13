package com.ad.util.client;

import com.ad.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


/**
 * @author sagupta
 *
 * Redis Client for Ad Server.
 */

@Slf4j
public class AdServerRedisClient {

  private static final String REDIS_HOST_PROPERTY = "jedis.connection.host";
  private static final String REDIS_PORT_PROPERTY = "jedis.connection.port";
  public static AdServerRedisClient adServerRedisClient = null;
  private static JedisPool jedisPool = null;

  private AdServerRedisClient() {

    final JedisPoolConfig jedisPoolConfig = buildPoolConfig();
    log.info("Redis Pool Init() :: {}   : Port :: {}",
        PropertiesUtil.getProperty(REDIS_HOST_PROPERTY),
        PropertiesUtil.getProperty(REDIS_PORT_PROPERTY));
    jedisPool = new JedisPool(jedisPoolConfig, PropertiesUtil.getProperty(REDIS_HOST_PROPERTY),
        Integer.parseInt(PropertiesUtil.getProperty(REDIS_PORT_PROPERTY)));

  }

  private JedisPoolConfig buildPoolConfig() {
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(128);
    poolConfig.setMaxIdle(128);
    poolConfig.setMinIdle(16);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
    poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);
    return poolConfig;
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
    Jedis rs = jedisPool.getResource();
    rs.set(key, value);
    if (seconds > 0) {
      rs.expire(key, seconds);
    }
    closeConnection(rs);
  }

  public String get(String key) {
    Jedis rs = jedisPool.getResource();
    String data = rs.get(key);
    closeConnection(rs);
    return data;
  }

  public void put(byte[] key, byte[] value, int seconds) {
    Jedis rs = jedisPool.getResource();
    String result = rs.set(key, value);
    log.info("redis byte inserts :: {}", result);
    if (seconds > 0) {
      rs.expire(key, seconds);
    }
    closeConnection(rs);
  }

  public byte[] get(byte[] key) {

    Jedis rs = jedisPool.getResource();
    byte[] data = rs.get(key);
    closeConnection(rs);
    return data;
  }

  private void closeConnection(Jedis jedis) {
    jedis.close();

  }

}
