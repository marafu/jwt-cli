package assis.matheus.artigo.jwt.unitary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.usecases.GenerateJWT;
import assis.matheus.artigo.jwt.interfaces.JSON;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;

class GenerateJWTTest {
    private GenerateJWT generateJWT;
    private JWT jwt;
    private JSON json;
    private Logger logger;

    @BeforeEach
    @DisplayName("Inicializa a instância de GenerateJWT com dependências mockadas")
    void setUp() {
        jwt = mock(JWT.class);
        json = mock(JSON.class);
        logger = mock(Logger.class);
        generateJWT = new GenerateJWT(jwt, json, logger);
    }

    @Test
    @DisplayName("Deve executar a geração do JWT sem lançar exceções")
    void shouldExecuteGenerateJWTWithoutExceptions() {
        String squad = "Security";
        String title = "Implementação Segura de JWT";
        String jsonPayload = "{\"squad\":\"Security\",\"title\":\"Implementação Segura de JWT\"}";
        String generatedToken = "mocked.jwt.token";
        String signingKey = "mocked-signing-key";

        when(json.create(any())).thenReturn(jsonPayload);
        when(jwt.generate(jsonPayload)).thenReturn(generatedToken);
        when(jwt.getSigningKey()).thenReturn(signingKey);

        assertDoesNotThrow(() -> generateJWT.execute(squad, title), "A execução não deve lançar exceções");

        verify(logger, times(1)).info("Gerando token JWT");
        verify(logger, times(1)).info("Token JWT gerado:");
        verify(logger, times(1)).info(generatedToken);
        verify(logger, times(1)).info("Recuperando a chave de assinatura:");
        verify(logger, times(1)).info(signingKey);
    }
}
