package com.ad.server.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

public enum TemplateType {

  MRAID("mraid.vm"),
  VAST("vast.vm"),
  VPAID("vpaid.vm");

  private final Template template;

  private static VelocityEngine engine;

  private TemplateType(final String template) {
    initEngine();
    this.template = Velocity.getTemplate(template);;
  }

  public Template getTemplate() {
    return template;
  }

  private void initEngine() {
    if (engine == null) {
      engine = new VelocityEngine();
      engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogSystem");
      engine.setProperty("runtime.log.logsystem.log4j.category", "com.ydsp.cache.engine.Velocity");
      engine.init();
    }
  }
}
