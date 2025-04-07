package assis.matheus.artigo.jwt.unitary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.valueobjects.Squad;

import static org.junit.jupiter.api.Assertions.*;

class SquadTest {

    @Test
    @DisplayName("Deve criar Squad com valor válido")
    void shouldCreateSquadWithValidValue() {
        String validValue = "DevSecOps";
        Squad squad = new Squad(validValue);
        assertEquals(validValue, squad.getValue());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor for nulo")
    void shouldThrowExceptionIfValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Squad(null),
                "O valor do Squad não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor for vazio")
    void shouldThrowExceptionIfValueIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Squad(""),
                "O valor do Squad não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor contiver apenas espaços")
    void shouldThrowExceptionIfValueIsWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> new Squad("   "),
                "O valor do Squad não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor for menor que 1 caracteres")
    void shouldThrowExceptionIfValueExceedsMinLength() {
        String longValue = "AA"; // Uma string com 1 caracteres
        assertThrows(IllegalArgumentException.class, () -> new Squad(longValue),
                "O valor do título não pode ser menor que 3 e exceder 50 caracteres.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor exceder 50 caracteres")
    void shouldThrowExceptionIfValueExceedsMaxLength() {
        String longValue = "A".repeat(51); // Uma string com 51 caracteres
        assertThrows(IllegalArgumentException.class, () -> new Squad(longValue),
                "O valor do título não pode ser menor que 3 e exceder 50 caracteres.");
    }
    
    @Test
    @DisplayName("Deve permitir valores com comprimento válido e sem caracteres perigosos")
    void shouldAllowValidValues() {
        // Valores válidos
        assertDoesNotThrow(() -> new Squad("DevSecOps"));
        assertDoesNotThrow(() -> new Squad(" Backend Squad "));
    }
}
