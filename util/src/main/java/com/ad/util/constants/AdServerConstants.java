package com.ad.util.constants;

/**
 * @author sagupta
 *
 * Ad Server Constants declaration.
 */

public class AdServerConstants {

  public interface General {

    final String NAMESPACE = "ad-server";
    final String DELIMITER = ":";
    //support only GET Method
    public static final String SUPPORTED_HTTP_REQUEST = "GET";
  }

  public interface DEVICE_ID_TYPE {

    int deviceId = 1;
    int cookieId = 2;
  }

}
