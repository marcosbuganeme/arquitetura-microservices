package dev.go.arquitetura.microservices.core.docs;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class BaseSwaggerConfig {

	private final String basePackage;

	public BaseSwaggerConfig(String basePackage) {
		this.basePackage = basePackage;
	}

	
	public @Bean Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
						.select()
						.apis(RequestHandlerSelectors.basePackage(basePackage))
						.build()
						.apiInfo(metaData());
	}

	private ApiInfo metaData() {

		return new ApiInfoBuilder()
						.title("Arquitetura micro serviços referência com spring cloud (netflix oss)")
						.description("Diversas ferramentas integradas ao spring boot. Ribbon, Zuul, Eureka, Hystrix")
						.version("1.0.0")
						.contact(new Contact("Marcos Olavo", "http://github.com/marcosbuganeme", "marcos.after@gmail.com"))
						.license("Free code")
						.licenseUrl("http://http://github.com/marcosbuganeme/arquitetura-microservices")
						.build();
	}
}