package dev.go.arquitetura.microservices.token.creator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.go.arquitetura.microservices.core.model.ApplicationUser;
import dev.go.arquitetura.microservices.core.security.JwtConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenCreator {

    @SneakyThrows
    public SignedJWT createSignedJWT(Authentication auth) {
        log.info("Começando a criar o JWT assinado");

        ApplicationUser user = (ApplicationUser) auth.getPrincipal();

        JWTClaimsSet jwtClaimSet = createJWTClaimSet(auth, user);

        KeyPair rsaKeys = gerarParChavePublicas();

        log.info("Construindo o JWK a partir das chaves RSA");

        JWK jwk = new RSAKey
        				.Builder((RSAPublicKey) rsaKeys.getPublic())
        				.keyID(UUID.randomUUID().toString())
        				.build();

        SignedJWT JwtAssinado = new SignedJWT(new JWSHeader
        											.Builder(JWSAlgorithm.RS256)
									                .jwk(jwk)
									                .type(JOSEObjectType.JWT)
									                .build(), jwtClaimSet);

        log.info("Assinando o token com a chave privada RSA");

        RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());

        JwtAssinado.sign(signer);

        log.info("Serialized token '{}'", JwtAssinado.serialize());

        return JwtAssinado;
    }

    private JWTClaimsSet createJWTClaimSet(Authentication auth, ApplicationUser user) {
        log.info("Criando o objeto JwtClaimSet para '{}'", user);

        return new JWTClaimsSet
        				.Builder()
        				.subject(user.getUsername())
        				.claim("authorities", auth
        										.getAuthorities()
						                        .stream()
						                        .map(GrantedAuthority::getAuthority)
						                        .collect(Collectors.toList()))
		                .claim("userId", user.getId())
		                .issuer("http://github.com/marcosbuganeme/arquitetura-microservices")
		                .issueTime(new Date())
		                .expirationTime(expiration())
		                .build();
    }

    public String criptografarToken(SignedJWT signedJWT) throws JOSEException {

    	log.info("Iniciando o Método de Token de Criptografia");

        DirectEncrypter directEncrypter = new DirectEncrypter(JwtConfiguration.privateKey.getBytes());
        JWEObject jsonWebEncrypt = new JWEObject(new JWEHeader
	        											.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
										                .contentType("JWT")
										                .build(), new Payload(signedJWT));

        log.info("Criptografar token com chave privada do sistema");

        jsonWebEncrypt.encrypt(directEncrypter);

        log.info("Token criptografado");

        return jsonWebEncrypt.serialize();
    }

    @SneakyThrows
    private KeyPair gerarParChavePublicas() {
    	log.info("Gerando chaves RSA 2048 bits");

    	KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    	generator.initialize(2048);

    	return generator.genKeyPair();
    }

	private Date expiration() {

		int tempoExpirar = JwtConfiguration.expiration * 1000;
		return new Date(System.currentTimeMillis() + tempoExpirar);
	}
}