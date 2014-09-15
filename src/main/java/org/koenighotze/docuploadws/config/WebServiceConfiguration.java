package org.koenighotze.docuploadws.config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {
	private static final String PORT_NAME = "DocumentServicePort";
	private static final String LOCATION_URI = "/service";
	private static final String NAMESPACE_URI = "http://koenighotze.org/docuservice";
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
