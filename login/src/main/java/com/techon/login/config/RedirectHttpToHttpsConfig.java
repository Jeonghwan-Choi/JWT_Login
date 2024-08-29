package com.techon.login.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedirectHttpToHttpsConfig implements
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

  @Override
  public void customize(TomcatServletWebServerFactory factory) {
    factory.addAdditionalTomcatConnectors(createHttpConnector());
  }

  private Connector createHttpConnector() {
    Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setScheme("http");
    connector.setPort(8000); // HTTP Port
    connector.setSecure(false);
    connector.setRedirectPort(8000); // HTTPS Port
    return connector;
  }
}
