package com.ad.server.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public enum TemplateType {

  MRAID(1, "mraid.vm"),
  VAST(2, "vast.vm"),
  BANNER(3, "banner.vm"),
  VPAID(4, "vpaid.vm"),
  NATIVE(5, "native.vm");

  private static VelocityEngine engine;
  private final Template template;
  private int id;

  private TemplateType(final int id, final String template) {
    this.id = id;
    initEngine();
    this.template = Velocity.getTemplate(template);
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
      engine.setProperty("runtime.log.logsystem.log4j.category", "com.ydsp.cache.engine.Velocity");
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

}
