package dev.go.arquitetura.microservices.token.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;

public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    protected @Override void configure(HttpSecurity http) throws Exception {

        http
        .csrf()
        	.disable()
        .cors()
        	.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        	.and()
        .sessionManagement()
        	.sessionCreationPolicy(STATELESS)
        	.and()
        .exceptionHandling()
        	.authenticationEntryPoint((req, resp, e) -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        	.and()
        .authorizeRequests()
	        .antMatchers(JwtConfiguration.loginUrl, "/**/swagger-ui.html")
	        	.permitAll()
	        .antMatchers(HttpMethod.GET, "/**/swagger-resources/**", "/**/webjars/springfox-swagger-ui/**", "/**/v2/api-docs/**")
	        	.permitAll()
	        .antMatchers("/v1/admin/**").hasRole("ADMIN")
	        .anyRequest().authenticated();
    }
}