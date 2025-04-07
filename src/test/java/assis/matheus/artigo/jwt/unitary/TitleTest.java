package assis.matheus.artigo.jwt.unitary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.valueobjects.Title;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest {

    @Test
    @DisplayName("Deve criar Title com valor válido")
    void shouldCreateTitleWithValidValue() {
        String validValue = "Título válido";
        Title title = new Title(validValue);
        assertEquals(validValue, title.getValue());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor for nulo")
    void shouldThrowExceptionIfValueIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Title(null),
                "O valor do título não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor for vazio")
    void shouldThrowExceptionIfValueIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Title(""),
                "O valor do título não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor contiver apenas espaços")
    void shouldThrowExceptionIfValueIsWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> new Title("   "),
                "O valor do título não pode ser nulo ou vazio.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor exceder 50 caracteres")
    void shouldThrowExceptionIfValueExceedsMaxLength() {
        String longValue = "A".repeat(351); 
        assertThrows(IllegalArgumentException.class, () -> new Title(longValue),
                "O valor do título não pode exceder 50 caracteres.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor exceder 50 caracteres")
    void shouldThrowExceptionIfValueExceedsMinLength() {
        String longValue = "AA"; 
        assertThrows(IllegalArgumentException.class, () -> new Title(longValue),
                "O valor do título não pode exceder 50 caracteres.");
    }

    @Test
    @DisplayName("Deve sanitizar caracteres perigosos (XSS, SQL Injection)")
    void shouldSanitizeDangerousCharacters() {
        String dangerousValue = "<script>alert('XSS'); DROP TABLE users; --</script>";
        Title title = new Title(dangerousValue);

        assertEquals("&lt;script&gt;alert&#40;&#39&#59;XSS&#39&#59;&#41;&#59; DROP TABLE users&#59; &#45;&#45;&lt;/script&gt;", title.getValue());
    }

    @Test
    @DisplayName("Deve sanitizar aspas simples")
    void shouldSanitizeSingleQuotes() {
        String dangerousValue = "Título com 'aspas simples'";
        Title title = new Title(dangerousValue);

        assertEquals("Título com &#39&#59;aspas simples&#39&#59;", title.getValue());
    }

    @Test
    @DisplayName("Deve sanitizar aspas duplas")
    void shouldSanitizeDoubleQuotes() {
        String dangerousValue = "Título com \"aspas duplas\"";
        Title title = new Title(dangerousValue);

        assertEquals("Título com &quot&#59;aspas duplas&quot&#59;", title.getValue());
    }

    @Test
    @DisplayName("Deve sanitizar caracteres perigosos como <, >, ( e )")
    void shouldSanitizeDangerousHtmlAndParenthesesCharacters() {
        String dangerousValue = "Valor com <html> e (parênteses)";
        Title title = new Title(dangerousValue);

        assertEquals("Valor com &lt;html&gt; e &#40;parênteses&#41;", title.getValue());
    }

    @Test
    @DisplayName("Deve permitir valores válidos com comprimento correto e sem caracteres perigosos")
    void shouldAllowValidValues() {
        String validValue = "Título comum sem caracteres perigosos.";
        Title title = new Title(validValue);

        assertEquals("Título comum sem caracteres perigosos.", title.getValue());
    }
}
