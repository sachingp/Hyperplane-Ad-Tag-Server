package com.ad.server.template;

import org.apache.velocity.Template;

public enum TagTemplateType {

  MRAID(1, "Mraid", "mraid-tag.vm"),
  VAST(2, "Vast", "vast-tag.vm"),
  BANNER(3, "Banner", "banner-tag.vm"),
  VPAID(4, "Vpaid", "vpaid-tag.vm"),
  NATIVE(5, "Native", "native-tag.vm");

  private final TemplateEngine engine;
  private final Template template;
  private final int id;
  private final String name;

  private TagTemplateType(final int id, final String name, final String template) {
    this.id = id;
    this.name = name;
    this.engine = TemplateEngine.getInstance();
    this.template = engine.getTemplate("templates/tag-templates/" + template);
  }

  public int getId() {
    return id;
  }

  public Template getTemplate() {
    return template;
  }

  public static TagTemplateType from(final int id) {
    final TagTemplateType[] values = TagTemplateType.values();
    for (final TagTemplateType value : values) {
      if (value.id == id) {
        return value;
      }
    }
    return null;
  }

  public static TagTemplateType from(final String name) {
    final TagTemplateType[] values = TagTemplateType.values();
    for (final TagTemplateType value : values) {
      if (value.name.equalsIgnoreCase(name)) {
        return value;
      }
    }
    return null;
  }

}
