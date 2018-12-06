package com.ad.util.constants;

import lombok.Getter;

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
    final String COOKIE_NAME = "hu-uid";
    final String COOKIE_PREFIX = "huiDAds";
    final String EMPTY_STRING = "";


  }

  public interface CACHE {

    final String ALL_ACTIVE_TAG_GUID_CACHE_KEY = "ALL_ACTIVE_TAG_GUID_CACHE_KEY";

    final String ALL_TAG_COUNTRY_CACHE_KEY = "ALL_TAG_COUNTRY_CACHE_KEY";

    final String TAG_GUID_CREATIVE_CACHE_KEY = "TAG_GUID_CREATIVE_CACHE_KEY";

  }

  public interface DEVICE_ID_TYPE {

    int deviceId = 1;
    int cookieId = 2;
  }

  @Getter
  public enum PARAMS {
    ORD("ord", "{CACHE_BUSTER}"),
    EXTERNAL_PLACEMENT_ID("extPlId", "{PLACEMENT_ID}"),
    EXTERNAL_CREATIVE_ID("extCrId", "{CREATIVE_ID}"),
    EXTERNAL_CAMPAIGN_ID("exCpId", "{CAMPAIGN_ID}"),
    EXTERNAL_BUNDLE_ID("extBnId", "{APP_BUNDLE}"),
    EXTERNAL_APP_ID("extApId", "{APP_ID}"),
    EXTERNAL_APP_NAME("extApNm", "{APP_NAME}"),
    EXTERNAL_SITE_ID("extStId", "{SITE_ID}"),
    GPS_LAT("gpsLat", "{LAT}"),
    GPS_LNG("gpsLng", "{LONG}"),
    DEVICE_ID("deviceId", "{MOBILE_ID}"),
    BUYER_ID("buyId", "{BUYER_ID}"),
    IOS_ID("iosId", "{IOS_ID}"),
    ANDROID_ID("androidId", "{ANDROID_ID}"),
    EXTERNAL_AD_SERVER("extAdServer", "{EXTERNAL_AD_SERVER}");

    private final String name;
    private final String macro;

    PARAMS(String name, String macro) {
      this.name = name;
      this.macro = macro;
    }

  }

  public interface DEVICE_MACROS_GROUP {

    final PARAMS[] DEVICES = {PARAMS.DEVICE_ID, PARAMS.IOS_ID, PARAMS.ANDROID_ID};
  }
}
