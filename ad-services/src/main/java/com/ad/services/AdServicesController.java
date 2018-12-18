package com.ad.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ad.server.pojo.CreativeTag;
import com.ad.server.repo.CreativeTagRepo;
import com.ad.server.repo.MacrosRepo;
import com.ad.server.template.TemplateService;
import com.ad.server.template.TemplateType;
import com.ad.services.cache.builder.RedisCacheBuilder;
import com.ad.services.exception.AdServicesException;

@Slf4j
@RestController
@SuppressWarnings({"unchecked", "rawtypes"})
public class AdServicesController {

  @Autowired
  private RedisCacheBuilder builder;

  @Value("${ads.domain}")
  private String domain;

  @Value("#{'${cache.names}'.split(',')}")
  private Set<String> cacheNames;

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

  @RequestMapping(value = "/ad-services/cache/state/list", produces = "text/html")
  public ResponseEntity list(final HttpServletRequest request) {
    final String baseURL = request.getRequestURI().replace("/list", "");
    final String response = AdServicesHtmlWriter.getMarkUp(baseURL, "Index", cacheNames);
    return new ResponseEntity(response, HttpStatus.OK);
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
      if (cache == null || cache.isEmpty()) {
        log.error("Invalid cache state: {}/{}", name, id);
        return new ResponseEntity("Invalid cache state: " + name + "/" + id, HttpStatus.NOT_FOUND);
      }
      final Object key = cache.keySet().iterator().next();
      if (Integer.class.isAssignableFrom(key.getClass())) {
        final Integer intId = Integer.parseInt(id);
        log.trace("Cache state: {}", cache.get(intId));
        return new ResponseEntity(cache.get(intId), HttpStatus.OK);
      }
      log.trace("Cache state: {}", cache.get(id));
      return new ResponseEntity(cache.get(id), HttpStatus.OK);
    } catch (final AdServicesException e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/ad-services/tag/{guid}/type/{name}/get", produces = "text/html")
  public ResponseEntity evaluate(@PathVariable("guid") final String guid, @PathVariable("name") final String name) {
    final TemplateType template = TemplateType.from(name);
    if (template == null) {
      log.info("Invalid template: {}", name);
      return new ResponseEntity("Invalid Template name: " + name, HttpStatus.NOT_FOUND);
    }
    try {
      final Map<String, Object> context = new HashMap<>();
      context.put("DOMAIN", domain);
      context.put("GUID", guid);
      final Map<Integer, Map<String, String>> macros = (Map<Integer, Map<String, String>>) builder.get(MacrosRepo.PARTNER_MACRO);
      final Map<String, CreativeTag> tags = (Map<String, CreativeTag>) builder.get(CreativeTagRepo.TAG_GUID_CREATIVE);
      final CreativeTag creativeTag = tags.get(guid);
      final String expanded;
      if (creativeTag == null) {
        expanded = "";
      } else {
        final Map<String, String> partnerMacros = macros.get(creativeTag.getAdPartnerId());
        final StringBuilder builder = new StringBuilder();
        partnerMacros.forEach((k, v) -> {
          builder.append("'").append(k).append("'='").append(v).append("',");
        });
        if (builder.length() > 1) {
          expanded = builder.substring(0, builder.length() - 1);
        } else {
          expanded = builder.toString();
        }
      }
      context.put("PARTNER_MACROS", expanded);
      final String evaluated = new TemplateService().eval(context, template);
      return new ResponseEntity(evaluated, HttpStatus.OK);
    } catch (final AdServicesException e) {
      return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}