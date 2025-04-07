package assis.matheus.artigo.jwt.unitary;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.exceptions.AuthenticationException;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;
import assis.matheus.artigo.jwt.usecases.ValidateJWT;

class ValidateJWTTest {
    private JWT jwtMock;
    private Logger loggerMock;
    private ValidateJWT validateJWT;

    @BeforeEach
    @DisplayName("Inicializa instâncias mockadas para testes")
    void setUp() {
        jwtMock = mock(JWT.class);
        loggerMock = mock(Logger.class);
        validateJWT = new ValidateJWT(jwtMock, loggerMock);
    }

    @Test
    @DisplayName("Deve validar corretamente um token JWT válido")
    void shouldValidateTokenSuccessfully() {
        String validToken = "valid.token.here";

        doNothing().when(jwtMock).validate(validToken);

        validateJWT.execute(validToken, "secret");

        verify(jwtMock).validate(validToken);
        verify(loggerMock).info("O token é válido");
    }

    @Test
    @DisplayName("Deve registrar erro ao tentar validar um token JWT inválido")
    void shouldLogErrorForInvalidToken() {
        String invalidToken = "invalid.token.here";

        doThrow(new AuthenticationException("Token inválido", null)).when(jwtMock).validate(invalidToken);

        validateJWT.execute(invalidToken, "secret");

        verify(jwtMock).validate(invalidToken);
        verify(loggerMock).info("O token não válido.");
    }
}
