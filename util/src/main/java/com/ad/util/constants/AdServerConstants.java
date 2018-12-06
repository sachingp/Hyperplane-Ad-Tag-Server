package com.ad.util.constants;

/**
 * @author sagupta
 *
 * Ad Server Constants declaration.
 */

public class AdServerConstants {

  public interface GENERAL {

    final String NAMESPACE = "ad-server";
    final String DELIMITER = ":";
    //support only GET Method
    public static final String SUPPORTED_HTTP_REQUEST = "GET";
    final String MAXMIND_GEO_DATABASE_LOCATION_PROPERTY_NAME = "maxmind.geo.database";


  }

  public interface CACHE {

    final String ALL_ACTIVE_TAG_GUID_CACHE_KEY = "ALL_ACTIVE_TAG_GUID_CACHE_KEY";

    final String ALL_TAG_COUNTRY_CACHE_KEY = "ALL_TAG_COUNTRY_CACHE_KEY";

  }

  public interface DEVICE_ID_TYPE {

    int deviceId = 1;
    int cookieId = 2;
  }

}
