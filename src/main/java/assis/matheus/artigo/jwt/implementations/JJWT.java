package assis.matheus.artigo.jwt.implementations;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import assis.matheus.artigo.jwt.exceptions.AuthenticationException;
import assis.matheus.artigo.jwt.exceptions.InvalidKeyUsedException;
import assis.matheus.artigo.jwt.exceptions.TokenExpiredException;
import assis.matheus.artigo.jwt.exceptions.TokenMalformedException;
import assis.matheus.artigo.jwt.exceptions.TokenUnsupportedException;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

public class JJWT implements JWT {
    private final Integer expirationTimeInHour;
    private final SecretKey key;
    private final Logger logger;

    public JJWT(Integer expirationTimeInHour, Logger logger) {
        this.expirationTimeInHour = expirationTimeInHour;
        this.key = Jwts.SIG.HS512.key().build();
        this.logger = logger;
    }

    public JJWT(Integer expirationTimeInHour, SecretKey key, Logger logger) {
        this.expirationTimeInHour = expirationTimeInHour;
        this.key = key;
        this.logger = logger;
    }

    public JJWT(Integer expirationTimeInHour, byte[] encodedKeyBytes, Logger logger) {
        this.expirationTimeInHour = expirationTimeInHour;
        this.key = Keys.hmacShaKeyFor(encodedKeyBytes);
        this.logger = logger;
    }

    public String generate(String payload) {
        try {
            return Jwts.builder()
                       .header().add("typ", "JWT").and()
                       .subject(payload)
                       .issuer("DevSecOps")
                       .audience().add("GrupoBoticario").add("#developers").add("#security-champions").and()
                       .issuedAt(new Date())
                       .expiration(new Date(System.currentTimeMillis() + (3600 * 1000L * this.expirationTimeInHour)))
                       .id(UUID.randomUUID().toString())
                       .signWith(this.key)
                       .compact();
        } catch (JwtException e) {
            throw new AuthenticationException("Erro ao gerar o JWT", e);
        }
    }

    public void validate(String token) {
        try {
            logger.debug("Entrando no método de validação");
            Jwts.parser()
                .verifyWith(this.key)
                .requireAudience("GrupoBoticario")
                .requireIssuer("DevSecOps")
                .build()
                .parse(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("O token expirou no momento definido pela claim 'exp'", e);
        } catch (MalformedJwtException e) {
            throw new TokenMalformedException("O token está malformado ou contém erros na estrutura do JWT", e);
        } catch (UnsupportedJwtException e) {
            throw new TokenUnsupportedException("O tipo ou algoritmo do token não é suportado", e);
        } catch (InvalidKeyException e) {
            throw new InvalidKeyUsedException("A chave fornecida para validação do token é inválida", e);
        }
    }

    public String restore(String token) {
        try {
            this.validate(token);
            return Jwts.parser()
                .verifyWith(this.key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Erro durante a restauração dos dados do JWT", e);
        }
    }

    public String getSigningKey() {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(this.key.getEncoded());
    }
    

    public SecretKey getRawKey() {
        return this.key;
    }

    public String getSigningAlgorithm() {
        return this.key.getAlgorithm();
    }
}
