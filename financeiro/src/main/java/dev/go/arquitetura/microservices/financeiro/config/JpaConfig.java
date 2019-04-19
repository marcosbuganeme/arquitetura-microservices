package dev.go.arquitetura.microservices.financeiro.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("dev.go.arquitetura.microservices")
@EntityScan({"dev.go.arquitetura.microservices.core.model"})
@EnableJpaRepositories({"dev.go.arquitetura.microservices.core.repository"})
public class JpaConfig {

}
