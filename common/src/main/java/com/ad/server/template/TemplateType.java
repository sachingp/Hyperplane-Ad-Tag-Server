package com.ad.server.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public enum TemplateType {

  MRAID(1, "Mraid", "mraid.vm"),
  VAST(2, "Vast", "vast.vm"),
  BANNER(3, "Banner", "banner.vm"),
  VPAID(4, "Vpaid", "vpaid.vm"),
  NATIVE(5, "Native", "native.vm");

  private static VelocityEngine engine;
  private final Template template;
  private final int id;
  private final String name;

  private TemplateType(final int id, final String name, final String template) {
    this.id = id;
    this.name = name;
    initEngine();
    this.template = Velocity.getTemplate("templates/" + template);
  }

  public int getId() {
    return id;
  }

  public Template getTemplate() {
    return template;
  }

  private void initEngine() {
    if (engine == null) {
      engine = new VelocityEngine();
      engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
          "org.apache.velocity.runtime.log.Log4JLogSystem");
      engine.setProperty("runtime.log.logsystem.log4j.category", "com.ad.server.engine.Velocity");
      engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
      engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//      engine.setProperty("resource.loader", "class");
//      engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      engine.init();
    }
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
      if (value.name.equals(name)) {
        return value;
      }
    }
    return null;
  }

}
