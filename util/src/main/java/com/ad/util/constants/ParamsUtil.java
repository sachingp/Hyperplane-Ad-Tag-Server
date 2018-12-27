package com.ad.util.constants;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sagupta
 */

public class ParamsUtil {

  public ParamsUtil() {
    throw new AssertionError("error while initializing the class");
  }

  /**
   * return params map to key value string.
   */

  public static String getParamsMapAsString(Map<String, String> params) {
    if (params != null && !params.isEmpty()) {

      String result = params.entrySet().stream()
          .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
      return result;
    }

    return null;

  }
}
