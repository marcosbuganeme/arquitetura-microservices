package dev.go.arquitetura.microservices.auth.filter;

import static java.util.Collections.emptyList;

import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;
import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import dev.go.arquitetura.microservices.token.creator.TokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenCreator tokenCreator;
    private final JwtConfiguration jwtConfiguration;
    private final AuthenticationManager authenticationManager;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, 
    											HttpServletResponse response) {

        log.info("Tentando autenticação ...");

        ApplicationUser user = new ObjectMapper()
        								.readValue(request.getInputStream(), ApplicationUser.class);

        if (Objects.isNull(user))
            throw new UsernameNotFoundException("Não é possível recuperar o nome de usuário ou senha");

        log.info("Criando o objeto de autenticação para o usuário '{}' e chamando UserDetailServiceImpl loadUserByUsername", user.getUsername());

        UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), 
        																									  user.getPassword(), 
        																									  emptyList());

        userAuthenticationToken.setDetails(user);

        return authenticationManager.authenticate(userAuthenticationToken);
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {

        log.info("A autenticação foi bem-sucedida para o usuário '{}', gerando o token assinado e criptografado", auth.getName());

        SignedJWT signedJWT = tokenCreator.createSignedJWT(auth);

        String tokenCriptografado = tokenCreator.criptografarToken(signedJWT);

        log.info("Token gerado com sucesso, adicionando-o ao cabeçalho de resposta");

        String header = jwtConfiguration.getHeader().getName();
        response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + header);

        response.addHeader(header, jwtConfiguration.getHeader().getPrefix() + tokenCriptografado);
    }
}