package com.ad.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.extern.slf4j.Slf4j;

import com.ad.util.exception.AdServicesSerializationException;

@SuppressWarnings({ "unchecked" })
@Slf4j
public class ObjectUtil {

  public static byte[] getBytes(final Object object) throws AdServicesSerializationException {
    // ObjectOutputStream is used to convert a Java object into OutputStream
    try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);) {
      oos.writeObject(object);
      return baos.toByteArray();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new AdServicesSerializationException("Exception while writing to byte array", e);
    }
  }

  public static <T> T readObject(final Class<T> type, final byte[] bytes)
      throws AdServicesSerializationException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);) {
      return (T) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      log.error(e.getMessage(), e);
      throw new AdServicesSerializationException("Exception while reading from byte array", e);
    }
  }

  public static byte[] combine(final byte[]... byteArrays) {
    if (byteArrays == null || byteArrays.length == 0) {
      log.info("Invalid bytes to combine.");
      return null;
    }
    int total = 0;
    for (final byte[] bytes : byteArrays) {
      if (bytes == null || bytes.length == 0) {
        continue;
      }
      total += bytes.length;
    }
    final byte[] wholeBytes = new byte[total];
    int destPos = 0;
    for (final byte[] bytes : byteArrays) {
      if (bytes == null || bytes.length == 0) {
        continue;
      }
      System.arraycopy(bytes, 0, wholeBytes, destPos, bytes.length);
      destPos += bytes.length;
    }
    return wholeBytes;
  }

}
