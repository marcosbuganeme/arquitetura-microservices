package dev.go.arquitetura.microservices.financeiro.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.go.arquitetura.microservices.token.config.SecurityTokenConfig;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;
import dev.go.arquitetura.microservices.token.filter.JwtTokenAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

    private final TokenConverter tokenConverter;

    public SecurityConfig(TokenConverter tokenConverter) {
        this.tokenConverter = tokenConverter;
    }
    
    protected @Override void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new JwtTokenAuthorizationFilter(tokenConverter), UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}