package dev.go.arquitetura.microservices.gateway.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;

import com.netflix.zuul.context.RequestContext;
import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;
import dev.go.arquitetura.microservices.token.filter.JwtTokenAuthorizationFilter;
import dev.go.arquitetura.microservices.token.util.SecurityContextUtil;
import lombok.SneakyThrows;

public class GatewayJwtTokenAuthorizationFilter extends JwtTokenAuthorizationFilter {

    public GatewayJwtTokenAuthorizationFilter(TokenConverter tokenConverter) {
        super(tokenConverter);
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
    								@NonNull HttpServletResponse response, 
    								@NonNull FilterChain chain) throws ServletException, IOException {

    	String prefixoJwt = JwtConfiguration.header.getPrefix();
        String header = request.getHeader(JwtConfiguration.header.getName());
        
        if (Objects.isNull(header) || !header.startsWith(prefixoJwt)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(prefixoJwt, "").trim();
        String signedToken = tokenConverter.decryptToken(token);
        tokenConverter.validateTokenSignature(signedToken);
        SecurityContextUtil.setSecurityContext(SignedJWT.parse(signedToken));

        if (JwtConfiguration.type.equalsIgnoreCase("signed"))

            RequestContext
            	.getCurrentContext()
            	.addZuulRequestHeader(JwtConfiguration.header.getName(), prefixoJwt + signedToken);

        chain.doFilter(request, response);
    }
}