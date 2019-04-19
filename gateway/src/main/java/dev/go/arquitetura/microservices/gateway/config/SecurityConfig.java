package dev.go.arquitetura.microservices.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.gateway.filter.GatewayJwtTokenAuthorizationFilter;
import dev.go.arquitetura.microservices.token.config.SecurityTokenConfig;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

    private final TokenConverter tokenConverter;

    @Autowired
    public SecurityConfig(JwtConfiguration jwtConfiguration, TokenConverter tokenConverter) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new GatewayJwtTokenAuthorizationFilter(tokenConverter, jwtConfiguration), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}