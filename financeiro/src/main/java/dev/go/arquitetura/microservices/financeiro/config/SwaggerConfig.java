package dev.go.arquitetura.microservices.financeiro.config;

import org.springframework.context.annotation.Configuration;

import dev.go.arquitetura.microservices.core.docs.BaseSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    public SwaggerConfig() {
        super("dev.go.arquitetura.microservices.financeiro.controller");
    }
}