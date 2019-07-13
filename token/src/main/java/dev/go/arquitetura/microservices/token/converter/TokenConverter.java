package dev.go.arquitetura.microservices.token.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenConverter {

    @SneakyThrows
    public String decryptToken(String encryptedToken) {

        log.info("Decodificando o token");

        JWEObject jweObject = JWEObject.parse(encryptedToken);
        DirectDecrypter directDecrypter = new DirectDecrypter(JwtConfiguration.privateKey.getBytes());
        jweObject.decrypt(directDecrypter);

        log.info("Token descriptografado, retornando token assinado ... ");

        return jweObject
        			.getPayload()
        			.toSignedJWT()
        			.serialize();
    }

    @SneakyThrows
    public void validateTokenSignature(String signedToken) {

        log.info("Iniciando o método para validar a assinatura do token ...");

        SignedJWT signedJWT = SignedJWT.parse(signedToken);

        log.info("Token analisado! Recuperando Chave Pública do Token Assinado");

        RSAKey publicKey = RSAKey.parse(signedJWT
        									.getHeader()
        									.getJWK()
        									.toJSONObject());

        log.info("Chave pública recuperada, validando assinatura ...");

        if (!signedJWT.verify(new RSASSAVerifier(publicKey)))
            throw new AccessDeniedException("Assinatura de token inválida!");

        log.info("O token tem uma assinatura válida");
    }
}