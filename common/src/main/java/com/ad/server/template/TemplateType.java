package com.ad.server.template;

import org.apache.velocity.Template;

public enum TemplateType {

  MRAID(1, "Mraid", "mraid.vm"),
  VAST(2, "Vast", "vast.vm"),
  BANNER(3, "Banner", "banner.vm"),
  VPAID(4, "Vpaid", "vpaid.vm"),
  NATIVE(5, "Native", "native.vm");

  private final TemplateEngine engine;
  private final Template template;
  private final int id;
  private final String name;

  private TemplateType(final int id, final String name, final String template) {
    this.id = id;
    this.name = name;
    this.engine = TemplateEngine.getInstance();
    this.template = engine.getTemplate("templates/" + template);
  }

  public int getId() {
    return id;
  }

  public Template getTemplate() {
    return template;
  }

  public static TemplateType from(final int id) {
    final TemplateType[] values = TemplateType.values();
    for (final TemplateType value : values) {
      if (value.id == id) {
        return value;
      }
    }
    return null;
  }

  public static TemplateType from(final String name) {
    final TemplateType[] values = TemplateType.values();
    for (final TemplateType value : values) {
      if (value.name.equalsIgnoreCase(name)) {
        return value;
      }
    }
    return null;
  }

}
