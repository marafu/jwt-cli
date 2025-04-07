package assis.matheus.artigo.jwt.unitary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.exceptions.AuthenticationException;
import assis.matheus.artigo.jwt.exceptions.InvalidKeyUsedException;
import assis.matheus.artigo.jwt.exceptions.TokenExpiredException;
import assis.matheus.artigo.jwt.exceptions.TokenMalformedException;
import assis.matheus.artigo.jwt.exceptions.TokenUnsupportedException;
import assis.matheus.artigo.jwt.implementations.JJWT;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;
import assis.matheus.artigo.jwt.utils.SoutLogger;
import io.jsonwebtoken.Jwts;

class JJWTTest {
    private JWT jwt;
    private Logger logger;
    private static SecretKey validKey;

    @BeforeEach
    @DisplayName("Inicializa a instância de JJWT com expiração de 1 hora")
    void setUp() {
        logger = new SoutLogger();
        jwt = new JJWT(1, logger);
        validKey = Jwts.SIG.HS256.key().build(); // Gera uma chave válida de 256 bits
    }

    @Test
    @DisplayName("Deve instanciar JJWT corretamente com SecretKey")
    void shouldInstantiateJJWTWithSecretKey() {
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        Integer expiration = 2;

        JJWT jjwt = new JJWT(expiration, secretKey, logger);

        assertNotNull(jjwt, "A instância de JJWT não deve ser nula");
    }

    @Test
    @DisplayName("Deve lançar AuthenticationException quando o token for gerado errado")
    void shouldThrowAuthenticationExceptionWhenGenerateTokenIsInvalid() {
        SecretKey secretKey = new SecretKeySpec(new byte[32], "HmacMD5");
        JJWT jwt = new JJWT(1, secretKey, logger);
        assertThrows(AuthenticationException.class, () -> jwt.generate("payload"));
    }

    @Test
    @DisplayName("Deve lançar TokenExpiredException quando o token estiver expirado")
    void shouldThrowTokenExpiredExceptionWhenTokenIsExpired() {
        JWT jwtExp = new JJWT(-1, validKey, logger);

        String expiredToken = jwtExp.generate("expired");

        JWT jwt = new JJWT(1, validKey, logger);

        assertThrows(TokenExpiredException.class, () -> jwt.validate(expiredToken));
    }

    @Test
    @DisplayName("Deve lançar TokenMalformedException quando o token for malformado")
    void shouldThrowTokenMalformedExceptionWhenTokenIsMalformed() {
        String malformedToken = "token.invalido";
        JJWT jjwt = new JJWT(1, validKey, logger);

        assertThrows(TokenMalformedException.class, () -> jjwt.validate(malformedToken));
    }

    @Test
    @DisplayName("Deve lançar AuthenticationException quando a restauração falhar")
    void shouldThrowAuthenticationExceptionWhenRestoreTokenFails() {
        JWT jwt = new JJWT(1, validKey, logger);
        assertThrows(AuthenticationException.class, () -> jwt.restore("token"));
    }


    @Test
    @DisplayName("Deve lançar TokenUnsupportedException quando o token for de um tipo não suportado")
    void shouldThrowTokenUnsupportedExceptionWhenTokenIsUnsupported() {
        String unsupportedToken = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJzdWIiOiJVc3XhcmnRlc3RlIiwiaXNzIjoiRGV2U2VjT3BzIiwiZXhwIjoxNzE3MjU1NDQwfQ.";
        JWT jjwt = new JJWT(1, validKey, logger);
        assertThrows(TokenUnsupportedException.class, () -> jjwt.validate(unsupportedToken));
    }


    @Test
    @DisplayName("Deve lançar InvalidKeyUsedException quando a chave for inválida")
    void shouldThrowInvalidKeyUsedExceptionWhenKeyIsInvalid() {
        SecretKey invalidKey = new SecretKeySpec(new byte[64], "HmacSHA512");
        String jwtInvalidToken = new JJWT(1, invalidKey, logger).generate("payload");
        JWT jjwt = new JJWT(1, validKey, logger);
        assertThrows(InvalidKeyUsedException.class, () -> jjwt.validate(jwtInvalidToken));
    }

    @Test
    @DisplayName("Deve instanciar JJWT corretamente com bytes codificados")
    void shouldInstantiateJJWTWithEncodedKeyBytes() {
        byte[] encodedKeyBytes = Jwts.SIG.HS256.key().build().getEncoded();
        Integer expiration = 2;
        JJWT jjwt = new JJWT(expiration, encodedKeyBytes, logger);
        assertNotNull(jjwt, "A instância de JJWT não deve ser nula");
    }

    @Test
    @DisplayName("Deve gerar um token JWT com payload válido")
    void shouldGenerateTokenWithValidPayload() {
        String payload = "test-payload";

        String token = jwt.generate(payload);

        assertNotNull(token, "O token gerado não deve ser nulo");
        assertFalse(token.isEmpty(), "O token gerado não deve ser vazio");
    }

    @Test
    @DisplayName("Deve validar corretamente um token JWT gerado")
    void shouldValidateGeneratedTokenSuccessfully() {
        String payload = "test-payload";
        String token = jwt.generate(payload);

        assertDoesNotThrow(() -> jwt.validate(token), "A validação não deve lançar exceções para um token válido");
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar um token JWT inválido")
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.here";

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> jwt.validate(invalidToken));
        assertNotNull(exception.getMessage(), "A mensagem da exceção não deve ser nula");
        assertTrue(exception.getMessage().contains("O token está malformado ou contém erros na estrutura do JWT"),
                "A mensagem da exceção deve indicar falha de verificação");
    }

    @Test
    @DisplayName("Deve restaurar o payload de um token JWT válido")
    void shouldRestoreValidTokenPayload() {
        String payload = "test-payload";
        String token = jwt.generate(payload);

        String restoredPayload = jwt.restore(token);

        assertEquals(payload, restoredPayload, "O payload restaurado deve ser igual ao payload original");
    }


    @Test
    @DisplayName("Deve lançar exceção para um token JWT expirado")
    void shouldThrowExceptionForExpiredToken() throws InterruptedException {

        JJWT shortLivedJwt = new JJWT(0, logger); // Expiração imediata
        String token = shortLivedJwt.generate("payload");

        Thread.sleep(2000); // Aguarda 2 segundos para o token expirar

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> shortLivedJwt.validate(token));
        assertTrue(exception.getMessage().contains("O token expirou no momento definido pela claim 'exp'"),
                "A mensagem da exceção deve indicar que o token expirou");
    }

    @Test
    @DisplayName("Deve retornar o algoritmo de assinatura utilizado pelo JWT")
    void shouldReturnSigningAlgorithm() {
        String algorithm = jwt.getSigningAlgorithm();
        assertEquals("HmacSHA512", algorithm, "O algoritmo de assinatura deve ser HmacSHA512");
    }

    @Test
    @DisplayName("Deve retornar a chave de assinatura utilizado pelo JWT")
    void shouldReturnSigningKeyValue() {
        String signingKey = jwt.getSigningKey();
        assertTrue(!signingKey.isEmpty() || !signingKey.isBlank());
    }

    @Test
    @DisplayName("Deve retornar a chave pura utilizado pelo JWT")
    void shouldReturnRawKey() {
        SecretKey key = jwt.getRawKey();
        assertNotNull(key);
    }
}
