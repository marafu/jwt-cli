package assis.matheus.artigo.jwt.unitary;


import static org.junit.jupiter.api.Assertions.*;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.exceptions.DomainException;
import assis.matheus.artigo.jwt.factory.JwtFactory;
import assis.matheus.artigo.jwt.interfaces.JWT;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

class JwtFactoryTest {

    private final JwtFactory jwtFactory = new JwtFactory();

    @Test
    @DisplayName("Deve retornar uma instância válida de JWT ao gerar um novo JWT")
    void shouldReturnValidJWTInstanceWhenGenerating() {
        JWT jwt = jwtFactory.getGenerateJWTFactory();
        assertNotNull(jwt, "A instância de JWT não deveria ser nula");
    }

    @Test
    @DisplayName("Deve retornar uma instância válida de JWT ao validar com chave válida")
    void shouldReturnValidJWTInstanceWhenValidatingWithValidKey() {
        SecretKey key = Keys.hmacShaKeyFor(new byte[32]); // Chave válida de 256 bits
        String encodedKey = Encoders.BASE64URL.encode(key.getEncoded());

        JWT jwt = jwtFactory.getValidateJWTFactory(encodedKey);
        assertNotNull(jwt, "A instância de JWT não deveria ser nula ao usar uma chave válida");
    }

    @Test
    @DisplayName("Deve lançar DomainException ao validar com chave inválida")
    void shouldThrowDomainExceptionWhenValidatingWithInvalidKey() {
        String invalidKey = "chave_invalida";

        DomainException exception = assertThrows(DomainException.class, () -> {
            jwtFactory.getValidateJWTFactory(invalidKey);
        });

        assertEquals("Não foi possível cri", exception.getMessage().substring(0, 20), "A exceção deveria indicar erro na decodificação");
    }
}