package com.ad.util.json;

import static org.junit.Assert.assertEquals;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.junit.Test;

public class JsonServiceTest {

  @Test
  public void testCreateJson() throws Exception {
    final Sample4Json object = new Sample4Json("id-value:1", "name-value:test");
    final String json = JsonService.createJson(object);
    final String expected = "{\"id\":\"id-value:1\",\"name\":\"name-value:test\"}";
    assertEquals(expected, json);
  }

  @AllArgsConstructor
  @Data
  private static class Sample4Json {
    private String id;
    private String name;
  }

}
