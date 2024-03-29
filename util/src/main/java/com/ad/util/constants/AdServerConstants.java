package com.ad.util.constants;

import lombok.Getter;

/**
 * @author sagupta
 *
 * Ad Server Constants declaration.
 */

public class AdServerConstants {

  @Getter
  public enum PARAMS {
    ORD("ord", "{{CACHE_BUSTER}}", "{CACHE_BUSTER}"),
    EXTERNAL_PLACEMENT_ID("extPlId", "{{PLACEMENT_ID}}", "{PLACEMENT_ID}"),
    EXTERNAL_CREATIVE_ID("extCrId", "{{CREATIVE_ID}}", "{CREATIVE_ID}"),
    EXTERNAL_CAMPAIGN_ID("exCpId", "{{CAMPAIGN_ID}}", "{CAMPAIGN_ID}"),
    EXTERNAL_BUNDLE_ID("extBnId", "{{APP_BUNDLE}}", "{APP_BUNDLE}"),
    EXTERNAL_APP_ID("extApId", "{{APP_ID}}", "{APP_ID}"),
    EXTERNAL_APP_NAME("extApNm", "{{APP_NAME}}", "{APP_NAME}"),
    EXTERNAL_SITE_ID("extStId", "{{SITE_ID}}", "{SITE_ID}"),
    GPS_LAT("gpsLat", "{{LAT}}", "{LAT}"),
    GPS_LNG("gpsLng", "{{LONG}}", "{LONG}"),
    DEVICE_ID("deviceId", "{{MOBILE_ID}}", "{MOBILE_ID}"),
    BUYER_ID("buyId", "{{BUYER_ID}}", "{BUYER_ID}"),
    IOS_ID("iosId", "{{IOS_ID}}", "{IOS_ID}"),
    ANDROID_ID("androidId", "{{ANDROID_ID}}", "{ANDROID_ID}"),
    EXTERNAL_AD_SERVER("extAdServer", "{{EXTERNAL_AD_SERVER}}", "{EXTERNAL_AD_SERVER}"),
    CLICK_THROUGH("clickUrl", "{{CLICK_URL}}", "{CLICK_URL}"),
    BID_ID("bidId", "{{AUCTION_ID}}", "{AUCTION_ID}"),
    DOMAIN("domain", "{{DOMAIN}}", "{DOMAIN}"),
    GUID("guid", "{{TAG_GUID}}", "{TAG_GUID}");

    private final String name;
    private final String macro;
    private final String macro_non_replace;

    PARAMS(String name, String macro, String macro_non_replace) {
      this.name = name;
      this.macro = macro;
      this.macro_non_replace = macro_non_replace;
    }

  }

  @Getter
  public enum MACROS {
    GUID("guid", "\\{TAG_GUID\\}"),
    EVENT_ID("eventId", "\\{EVENT_ID\\}"),
    SESSION_ID("sessionId", "\\{SESSION_ID\\}"),
    PARAMS("Params", "<Params>"),
    IMAGE_URL_1("imageURL1", "<imageURL1>"),
    WIDTH("width", "<width>"),
    HEIGHT("height", "<height>"),
    THIRD_PARTY_CLK_TRACK("clkTracker", "<clkTracker>");

    private final String name;
    private final String macro;

    MACROS(String name, String macro) {
      this.name = name;
      this.macro = macro;
    }

  }

  public interface GENERAL {

    final String NAMESPACE = "ad-server";
    final String DELIMITER = ":";
    //support only GET Method
    public static final String SUPPORTED_HTTP_REQUEST = "GET";
    final String MAXMIND_GEO_DATABASE_LOCATION_PROPERTY_NAME = "maxmind.geo.database";
    final String COOKIE_NAME = "hu-uid";
    final String COOKIE_PREFIX = "huiDAds";
    final String EMPTY_STRING = "";
    final long COOKIE_MAX_AGE = 7776000; // 90 days
    final String SECOND_DATE_FORMAT = "yyyyMMddHHmmss";
    final String HOUR_DATE_FORMAT = "yyyyMMddHH";
    final int REDIS_TTL_SESSSION = 21600;
    final String SIZE_DELIMITER = "x";


  }

  public interface CACHE {

    final String ALL_ACTIVE_TAG_GUID_CACHE_KEY = "ALL_ACTIVE_TAG_GUID_CACHE_KEY";

    final String ALL_TAG_COUNTRY_CACHE_KEY = "ALL_TAG_COUNTRY_CACHE_KEY";

    final String TAG_GUID_CREATIVE_CACHE_KEY = "TAG_GUID_CREATIVE_CACHE_KEY";

    final String TAG_PARTNER_CACHE_KEY = "TAG_PARTNER_CACHE_KEY";

    final String PARTNER_MACROS_CACHE_KEY = "PARTNER_MACROS_CACHE_KEY";

    final String EXCLUSION = "Exclusion";

    final String INCLUSION = "Inclusion";

    final long TAG_CACHE_TTL = 1800000;

  }

  public interface DEVICE_ID_TYPE {

    int deviceId = 1;
    int cookieId = 2;
  }

  public interface DEVICE_MACROS_GROUP {

    final PARAMS[] DEVICES = {PARAMS.DEVICE_ID, PARAMS.IOS_ID, PARAMS.ANDROID_ID};
  }
}
