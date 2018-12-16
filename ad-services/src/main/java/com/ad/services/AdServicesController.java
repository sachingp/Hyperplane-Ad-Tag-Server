package com.ad.services;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ad.server.template.TemplateService;
import com.ad.server.template.TemplateType;
import com.ad.services.cache.builder.RedisCacheBuilder;
import com.ad.services.exception.AdServicesException;

@RestController
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdServicesController {

  @Autowired
  private RedisCacheBuilder builder;

  @Value("${ads.domain}")
  private String domain;

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

  @RequestMapping(value = "/ad-services/cache/state/{cache-name}", produces = "text/html")
  public ResponseEntity state(@PathVariable("cache-name") final String name, final HttpServletRequest request) {
    try {
      final Map cache = (Map) builder.get(name);
      final String baseURL = request.getRequestURI();
      final String response = AdServicesHtmlWriter.getMarkUp(baseURL, name, cache.keySet());
      return new ResponseEntity(response, HttpStatus.OK);
    } catch (final AdServicesException e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/ad-services/cache/state/{cache-name}/{id}", produces = "application/json")
  public ResponseEntity state(@PathVariable("cache-name") final String name, @PathVariable("id") final String id) {
    try {
      final Map cache = (Map) builder.get(name);
      return new ResponseEntity(cache.get(id), HttpStatus.OK);
    } catch (final AdServicesException e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping("/ad-services/tag/{guid}/type/{name}")
  public ResponseEntity evaluate(@PathVariable("guid") final String guid, @PathVariable("name") final String name) {
    final TemplateType template = TemplateType.from(name);
    final Map<String, Object> context = new HashMap<>();
    context.put("DOMAIN", domain);
    context.put("GUID", guid);
    final String evaluated = new TemplateService().eval(context, template);
    return new ResponseEntity(evaluated, HttpStatus.OK);
  }

}