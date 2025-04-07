package assis.matheus.artigo.jwt.factory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.SecretKey;

import assis.matheus.artigo.jwt.exceptions.DomainException;
import assis.matheus.artigo.jwt.implementations.JJWT;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;
import assis.matheus.artigo.jwt.utils.SoutLogger;
import io.jsonwebtoken.security.Keys;

public class JwtFactory {
    public JwtFactory() {
    }

    public JWT getGenerateJWTFactory() {
        Logger logger = new SoutLogger();
        String encodedKeyBytes = UUID.randomUUID().toString();
        return new JJWT(1, encodedKeyBytes.getBytes(StandardCharsets.UTF_8), logger);

    }

    public JWT getValidateJWTFactory(String secret) {
        try {
            Logger logger = new SoutLogger();
    
            byte[] decodedKey = Base64.getUrlDecoder().decode(secret);
            
            SecretKey key = Keys.hmacShaKeyFor(decodedKey);

            logger.debug(Base64.getUrlEncoder().withoutPadding().encodeToString(key.getEncoded()));
    
            return new JJWT(1, key, logger);
        } catch (IllegalArgumentException e) {
            throw new DomainException("Chave secreta inválida para Base64URL: [" + secret + "]", e);
        } catch (Exception e) {
            throw new DomainException("Não foi possível criar a factory de validate: ", e);
        }
    }
    
}
