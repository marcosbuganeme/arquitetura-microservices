package dev.go.arquitetura.microservices.auth.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.go.arquitetura.microservices.auth.filter.JwtUsernameAndPasswordAuthenticationFilter;
import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.token.config.SecurityTokenConfig;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;
import dev.go.arquitetura.microservices.token.creator.TokenCreator;
import dev.go.arquitetura.microservices.token.filter.JwtTokenAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends SecurityTokenConfig {

    private final TokenCreator tokenCreator;
    private final TokenConverter tokenConverter;
    private final UserDetailsService userService;

    public SecurityConfig(TokenCreator tokenCreator, 
    					  TokenConverter tokenConverter,
    					  JwtConfiguration jwtConfiguration,
    					  @Qualifier("userDetailsServiceImpl") UserDetailsService userService) {

		super(jwtConfiguration);
		this.tokenCreator = tokenCreator;
		this.tokenConverter = tokenConverter;
		this.userService = userService;
	}

    protected @Override void configure(HttpSecurity http) throws Exception {
        http
        .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(tokenCreator, jwtConfiguration, authenticationManager()))
        .addFilterAfter(new JwtTokenAuthorizationFilter(tokenConverter, jwtConfiguration), UsernamePasswordAuthenticationFilter.class);

        super.configure(http);
    }

    protected @Override void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
        .userDetailsService(userService)
        .passwordEncoder(passwordEncoder());
    }

    public @Bean BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}