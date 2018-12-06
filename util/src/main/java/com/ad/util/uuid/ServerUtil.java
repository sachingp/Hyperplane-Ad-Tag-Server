package com.ad.util.uuid;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author sagupta
 */

@Slf4j
public class ServerUtil {

  private static char separator;
  private static StringBuilder IPAddressSegment;
  private static SecureRandom prng;

  static {
    try {
      // Get IPAddress Segment
      IPAddressSegment = new StringBuilder();
      InetAddress addr = InetAddress.getLocalHost();
      StringBuilder strTemp = new StringBuilder();
      byte[] ipaddr = addr.getAddress();
      for (int i = 0; i < ipaddr.length; i++) {
        Byte b = new Byte(ipaddr[i]);

        strTemp = new StringBuilder(
            Integer.toHexString(b.intValue() & 0x000000ff));
        while (strTemp.length() < 2) {
          strTemp.insert(0, '0');
        }
        IPAddressSegment.append(strTemp);
      }

      if (separator != '\u0000') {
        IPAddressSegment.append(separator);
      }

      // Get Random Segment Algoritm
      prng = SecureRandom.getInstance("SHA1PRNG");

    } catch (UnknownHostException ex) {
      log.error("Unknown Host Exception Caught: " + ex.getMessage());
    } catch (NoSuchAlgorithmException nsae) {
      log.error("No Such Algorithm Exception Caught: "
          + nsae.getMessage());
    }
  }


  /**
   * @return generate UUID.
   */
  public String generateGuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();

  }

  /**
   * @return 32 bit unique key
   */

  public static Long generateUniqueKey() throws Exception {
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      String ip = inetAddress.getHostAddress();
      UUID u = UUID.randomUUID();
      Long id = System.currentTimeMillis() + System.nanoTime();
      return u.toString().hashCode() + id + ip.hashCode();
    } catch (Exception e) {
      throw new Exception(e);
    }

  }

  /**
   * @return unique key generator
   */
  public static final String getUID() {
    StringBuilder strRetVal = new StringBuilder(IPAddressSegment);
    StringBuilder strTemp = new StringBuilder();

    // Get CurrentTimeMillis() segment
    strTemp = new StringBuilder(
        Long.toHexString(System.currentTimeMillis()));
    while (strTemp.length() < 12) {
      strTemp.insert(0, '0');
    }
    strRetVal.append(strTemp);
    if (separator != '\u0000') {
      IPAddressSegment.append(separator);
    }

    // Get Random Segment
    strTemp = new StringBuilder(Integer.toHexString(prng.nextInt()));
    while (strTemp.length() < 8) {
      strTemp.insert(0, '0');
    }

    strRetVal.append(strTemp.substring(4));
    if (separator != '\u0000') {
      IPAddressSegment.append(separator);
    }

    // Get IdentityHash() segment
    strTemp = new StringBuilder(Long.toHexString(System
        .identityHashCode((Object) new ServerUtil())));
    while (strTemp.length() < 8) {
      strTemp.insert(0, '0');
    }
    strRetVal.append(strTemp);

    return strRetVal.toString().toUpperCase();
  }


}
