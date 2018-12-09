package com.ad.services.cache.buider;

import static com.ad.util.ObjectUtil.getBytes;
import static com.ad.util.ObjectUtil.readObject;
import static com.ad.util.ObjectUtil.combine;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.ad.services.cache.AbstractCacheBuilder;
import com.ad.services.cache.type.Key;
import com.ad.services.cache.type.Value;
import com.ad.services.exception.AdServicesException;
import com.ad.services.exception.AdServicesRedisException;
import com.ad.util.client.AdServerRedisClient;

@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class RedisCacheBuilder<T> extends AbstractCacheBuilder<T> {

  private final byte[] partKeyBytes = "-parts".getBytes();
  private final byte[] partIndexKeyBytes = "-part-".getBytes();
  @org.springframework.beans.factory.annotation.Value("${cache.namespace}")
  private String namespace;
  @org.springframework.beans.factory.annotation.Value("${cache.packet.size.in.bytes}")
  private int packetSize;
  private final AdServerRedisClient client;

  public RedisCacheBuilder() throws AdServicesRedisException {
    client = AdServerRedisClient.getInstance();
  }

  @Override
  protected void write(final String name, final Key key, final Value value) throws AdServicesException {
    try {
      final Object keyValue = key.get();
      if (keyValue == null) {
        log.error("Key is null for {}", name);
        return;
      }
      log.info("Writing to cache: {} with key: {}", name, key.get());
      final byte[] bytes = getBytes(value.get());
      final int length = bytes.length;
      if (length > packetSize) {
        final int parts = length / packetSize;
        log.info("Cache size exceeds packet size for: {} with size: {} and parts: {}", name, length,
            parts + 1);
        int i = 0, start = 0, end = packetSize;
        while (i < parts) {
          final byte[] partBytes = new byte[end - start];
          log.info("Copying from start: {} of length: {}", start, end - start);
          System.arraycopy(bytes, start, partBytes, 0, end - start);
          start = end;
          end += packetSize;
          log.debug("Writing to part cache: {} with key: {}", name,
              String.valueOf(keyValue) + new String(partIndexKeyBytes) + i);
          client.put(combine(getBytes(keyValue), partIndexKeyBytes, new byte[]{(byte) i}), partBytes, 15 * 60);
          i++;
        }
        final byte[] remainderBytes = new byte[length - start];
        log.debug("Copying last packet from start: {} of length: {}", start, length - start);
        System.arraycopy(bytes, start, remainderBytes, 0, length - start);
        log.debug("Writing to part cache: {} with key: {}", name,
            String.valueOf(keyValue) + new String(partIndexKeyBytes) + i);
        client.put(combine(getBytes(keyValue), partIndexKeyBytes, new byte[]{(byte) i}), remainderBytes, 15 * 60);
        log.debug("Writing to part cache-key: {} with key: {}", name,
            String.valueOf(keyValue) + new String(partKeyBytes));
        client.put(combine(getBytes(keyValue), partKeyBytes), getBytes(parts + 1), 15 * 60);
      } else {
        log.info("Cache size within packet size for: {} with size: {}", name, length);
        client.put(getBytes(keyValue), bytes, 15 * 60);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new AdServicesException(e.getMessage(), e);
    }
  }

  @Override
  protected T read(final String name, final Key key) throws AdServicesException {
    try {
      final Object keyValue = key.get();
      if (keyValue == null) {
        log.error("Key is null for {}", name);
        return null;
      }
      final byte[] partRecord = client.get(combine(getBytes(keyValue), partKeyBytes));
      if (partRecord == null) {
        log.debug("Parting record not available. Fetching full data for: {}", keyValue);
        final byte[] record = client.get(getBytes(keyValue));
        return (T) readObject(Object.class, (byte[]) record);
      }
      final int parts = (int) readObject(Object.class, partRecord);
      log.debug("Parting record available. Fetching data parts: {}", keyValue);
      int i = 0;
      byte[] wholeBytes = new byte[0];
      while (i < parts) {
        final byte[] record = client.get(combine(getBytes(keyValue), partIndexKeyBytes, new byte[]{(byte) i}));
        log.debug("Fetching part data for: {}", keyValue + new String(partIndexKeyBytes) + i);
        wholeBytes = combine(wholeBytes, record);
        i++;
      }
      return (T) readObject(Object.class, wholeBytes);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new AdServicesException(e.getMessage(), e);
    }
  }

  public T get(final String name) throws AdServicesException {
    return read(name, new Key(name));
  }

}
