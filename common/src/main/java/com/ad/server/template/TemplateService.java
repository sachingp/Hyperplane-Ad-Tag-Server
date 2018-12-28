package com.ad.server.template;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;

@Component("templateService")
public class TemplateService {

  public String eval(final Map<String, Object> context, final TagTemplateType type) {
    final Template template = type.getTemplate();
    return eval(context, template);
  }

  public String eval(final Map<String, Object> context, final CreativeTemplateType type) {
    final Template template = type.getTemplate();
    return eval(context, template);
  }

  private String eval(final Map<String, Object> context, final Template template) {
    final VelocityContext velocity = new VelocityContext();
    context.forEach((k, v) -> {
      velocity.put(k, v);
    });
    final StringWriter writer = new StringWriter();
    template.merge(velocity, writer);
    final String expanded = writer.toString();
    return expanded;
  }

}
