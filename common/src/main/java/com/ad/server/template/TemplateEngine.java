package com.ad.server.template;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class TemplateEngine {

  private static TemplateEngine _self;

  private final VelocityEngine engine;

  private TemplateEngine() {
    engine = new VelocityEngine();
    engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
        "org.apache.velocity.runtime.log.Log4JLogSystem");
    engine.setProperty("runtime.log.logsystem.log4j.category", "com.ad.server.engine.Velocity");
    engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    engine.init();
  }

  public static TemplateEngine getInstance() {
    if (_self == null) {
      synchronized (TemplateEngine.class) {
        if (_self == null) {
          _self = new TemplateEngine();
        }
      }
    }
    return _self;
  }

  public Template getTemplate(final String name) {
    return engine.getTemplate(name);
  }

}
