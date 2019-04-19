package dev.go.arquitetura.microservices.financeiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.token.config.SecurityTokenConfig;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;
import dev.go.arquitetura.microservices.token.filter.JwtTokenAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

    private final TokenConverter tokenConverter;

    @Autowired
    public SecurityConfig(TokenConverter tokenConverter, JwtConfiguration jwtConfiguration) {
        super(jwtConfiguration);
        this.tokenConverter = tokenConverter;
    }
    
    protected @Override void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new JwtTokenAuthorizationFilter(tokenConverter, jwtConfiguration), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}