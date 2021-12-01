package com.quickguru.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
  
@Configuration
public class ServerConfiguration {  
      
	@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
                
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }
	
	@Bean
	WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
	  return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
	    @Override
	    public void customize(TomcatServletWebServerFactory tomcatServletWebServerFactory) {
	      tomcatServletWebServerFactory.addContextCustomizers(new TomcatContextCustomizer() {
	        @Override
	        public void customize(Context context) {
	          context.setCookieProcessor(new LegacyCookieProcessor());
	        }
	      });
	    }
	  };
	}
	
    private Connector getHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8081);
        connector.setSecure(false);
        connector.setRedirectPort(8445);
        return connector;
    }
}


