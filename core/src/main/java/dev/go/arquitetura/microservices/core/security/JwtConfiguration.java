package dev.go.arquitetura.microservices.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration {

	private int expiration = 3600;
	private String loginUrl = "/login/**";
	private String type = "encrypted";
	private String privateKey = "qxBEEQv7E8aviX1KUcdOiF5ve5COUPAr";
	private @NestedConfigurationProperty Header header = new Header();
}