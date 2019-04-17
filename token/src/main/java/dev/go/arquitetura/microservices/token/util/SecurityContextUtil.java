package dev.go.arquitetura.microservices.token.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityContextUtil {

	private SecurityContextUtil() {}

    public static void setSecurityContext(SignedJWT signedJWT) {
        try {

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String username = claims.getSubject();

            if (Objects.isNull(username))
                throw new JOSEException("Nome de usuário está faltando no JWT");

            List<String> autorizacoes = claims.getStringListClaim("authorities");
            ApplicationUser user = ApplicationUser
    									.builder()
						                    .id(claims.getLongClaim("userId"))
						                    .username(username)
						                    .role(String.join(",", autorizacoes))
						                    .build();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, criar(autorizacoes));
            auth.setDetails(signedJWT.serialize());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
        	log.error("Erro ao definir contexto de segurança ", e);
            SecurityContextHolder.clearContext();
        }
    }

    private static List<SimpleGrantedAuthority> criar(List<String> autorizacoes) {

        return autorizacoes
        		.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}