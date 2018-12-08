package com.ad.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;


@Slf4j
public class PropertiesUtil {

  private static final String APPLICATION_PROPERTY_FILE_NAME = "server.properties";

  private static final String DEFAULT_PROPERTY_FILE_NAME = "application-dev.properties";


  static Properties props = null;

  static {
    try {
      props = new Properties();
      String prop = Optional.ofNullable(System.getProperty(APPLICATION_PROPERTY_FILE_NAME))
          .orElse(DEFAULT_PROPERTY_FILE_NAME);

      InputStream is = PropertiesUtil.class.getClassLoader()
          .getResourceAsStream(prop);
      props.load(is);
    } catch (Exception e) {
      log.error("Error in loading properties file " + e.getLocalizedMessage());
    }
  }

  /**
   * @return property value
   */
  public static String getProperty(String propertyName) {
    if (props != null && props.containsKey(propertyName)) {
      return (String) props.get(propertyName);
    } else {
      return null;
    }
  }

}
