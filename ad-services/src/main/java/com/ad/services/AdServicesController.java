package com.ad.services;

import com.ad.services.cache.builder.RedisCacheBuilder;
import com.ad.services.exception.AdServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdServicesController {

  @Autowired
  private RedisCacheBuilder builder;

  @RequestMapping("/ad-services/healthcheck")
  public ResponseEntity healthcheck() {
    return new ResponseEntity("All OK !!", HttpStatus.OK);
  }

  @RequestMapping("/ad-services/status")
  public ResponseEntity status() {
    return new ResponseEntity(builder.getStats(), HttpStatus.OK);
  }

  @RequestMapping("/ad-services/status/{cache-name}")
  public ResponseEntity cache(@PathVariable("cache-name") final String name) {
    try {
      return new ResponseEntity(builder.get(name).toString(), HttpStatus.OK);
    } catch (final AdServicesException e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}