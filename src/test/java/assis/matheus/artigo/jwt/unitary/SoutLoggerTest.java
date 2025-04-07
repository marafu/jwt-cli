package assis.matheus.artigo.jwt.unitary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.interfaces.Logger;
import assis.matheus.artigo.jwt.utils.SoutLogger;

class SoutLoggerTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Logger logger;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        logger = new SoutLogger();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Deve registrar mensagem de debug corretamente")
    void shouldLogDebugMessage() {
        logger.debug("Debug message");
        assertTrue(outputStream.toString().contains("[DEBUG]: Debug message"));
    }

    @Test
    @DisplayName("Deve registrar mensagem de info corretamente")
    void shouldLogInfoMessage() {
        logger.info("Info message");
        assertTrue(outputStream.toString().contains("[INFO]: Info message"));
    }

    @Test
    @DisplayName("Deve registrar mensagem de erro corretamente")
    void shouldLogErrorMessage() {
        logger.error("Error message", null);
        assertTrue(outputStream.toString().contains("[ERROR]: Error message"));
    }

    @Test
    @DisplayName("Deve registrar mensagem de erro sem exceção")
    void shouldLogErrorMessageWithoutException() {
        logger.error("Error message");
        assertTrue(outputStream.toString().contains("[ERROR]: Error message"));
    }

    @Test
    @DisplayName("Deve registrar erro com stacktrace corretamente")
    void shouldLogErrorWithException() {
        Exception exception = new RuntimeException("Test exception");
        logger.error("Error with exception", exception);

        String output = outputStream.toString();
        assertTrue(output.contains("[ERROR]: Error with exception"));
        assertTrue(output.contains("Stacktrace:"));
        assertTrue(output.contains("java.lang.RuntimeException: Test exception"));
    }
}
