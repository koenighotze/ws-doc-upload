package org.koenighotze.docuploadws.config;

import org.springframework.boot.web.servlet.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.ws.config.annotation.*;
import org.springframework.ws.transport.http.*;
import org.springframework.ws.wsdl.wsdl11.*;
import org.springframework.xml.xsd.*;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean dispatcherServlet(
            ApplicationContext applicationContext) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "documents")
    public DefaultWsdl11Definition defaultWsdl11Definition(
            XsdSchema documentSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("DocumentsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition
                .setTargetNamespace("http://koenighotze.org/docuservice");
        wsdl11Definition.setSchema(documentSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema documentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("document.xsd"));
    }
}
