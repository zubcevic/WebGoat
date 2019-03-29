package org.owasp.webwolf;

import lombok.extern.slf4j.Slf4j;
import org.owasp.webwolf.requests.WebWolfTraceRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class WebWolf extends SpringBootServletInitializer {

    @Bean
    public TraceRepository traceRepository() {
        return new WebWolfTraceRepository();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebWolf.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebWolf.class, args);
    }
    
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.addConnectorCustomizers(
        		(TomcatConnectorCustomizer) connector -> connector.setProperty("sendReasonPhrase","true"));
        return factory;
    }
}
