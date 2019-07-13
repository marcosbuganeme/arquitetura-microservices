package dev.go.arquitetura.microservices.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfiguration {

	public static final int expiration = 3600;
	public static final String type = "encrypted";
	public static final String loginUrl = "/auth/**";
	public static final String privateKey = "qxBEEQv7E8aviX1KUcdOiF5ve5COUPAr";

	@NestedConfigurationProperty
	public static final Header header = new Header();
}