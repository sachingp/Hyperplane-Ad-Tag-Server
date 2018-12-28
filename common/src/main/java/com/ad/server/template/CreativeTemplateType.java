package com.ad.server.template;

import org.apache.velocity.Template;

public enum CreativeTemplateType {

  INTERSTITIAL(1, "Interstitial", "creative-interstitial.vm");

  private final TemplateEngine engine;
  private final Template template;
  private final int id;
  private final String name;

  private CreativeTemplateType(final int id, final String name, final String template) {
    this.id = id;
    this.name = name;
    this.engine = TemplateEngine.getInstance();
    this.template = engine.getTemplate("templates/creative-templates/" + template);
  }

  public int getId() {
    return id;
  }

  public Template getTemplate() {
    return template;
  }

  public static CreativeTemplateType from(final int id) {
    final CreativeTemplateType[] values = CreativeTemplateType.values();
    for (final CreativeTemplateType value : values) {
      if (value.id == id) {
        return value;
      }
    }
    return null;
  }

  public static CreativeTemplateType from(final String name) {
    final CreativeTemplateType[] values = CreativeTemplateType.values();
    for (final CreativeTemplateType value : values) {
      if (value.name.equalsIgnoreCase(name)) {
        return value;
      }
    }
    return null;
  }

}
