package com.ad.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sagupta
 */
@Slf4j
public class JsonService {

  private static final ObjectMapper mapper = new ObjectMapper();

  public JsonService() {

    throw new AssertionError("Error while initializing the class");

  }

  /**
   * @return json representation of the object.
   */

  public static String createJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

}
