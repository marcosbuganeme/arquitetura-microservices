package dev.go.arquitetura.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;

@SpringBootApplication
@ComponentScan("dev.go.arquitetura.microservices")
@EnableConfigurationProperties(value = JwtConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}