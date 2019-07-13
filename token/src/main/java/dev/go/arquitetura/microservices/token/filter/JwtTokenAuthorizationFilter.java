package dev.go.arquitetura.microservices.token.filter;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.token.converter.TokenConverter;
import dev.go.arquitetura.microservices.token.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

	protected final TokenConverter tokenConverter;

    @Override
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

        SecurityContextUtil.setSecurityContext(equalsIgnoreCase("signed", JwtConfiguration.type) ? validate(token) : decryptValidating(token));

        chain.doFilter(request, response);
    }

    private @SneakyThrows SignedJWT decryptValidating(String encryptedToken) {

        String signedToken = tokenConverter.decryptToken(encryptedToken);
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }

    private @SneakyThrows SignedJWT validate(String signedToken) {
        tokenConverter.validateTokenSignature(signedToken);
        return SignedJWT.parse(signedToken);
    }
}