package dev.go.arquitetura.microservices.auth.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableEurekaClient
public class EurekaConfig {
	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("marcos123"));
	}
}
