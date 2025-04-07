package assis.matheus.artigo.jwt.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import assis.matheus.artigo.jwt.JwtCLI;

class JwtCLITest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        if (outContent == null) { // Garante que não é nulo antes de resetar
            outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
        } else {
            outContent.reset(); // Apenas reseta se já estiver inicializado
        }
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Deve imprimir no console o help")
    void shouldGenerateHelp() {
        outContent.reset(); // Limpa buffer antes do teste

        JwtCLI.main(new String[] { "--help" });

        System.out.flush(); // Garante que toda saída foi escrita

        String output = outContent.toString();
        System.out.println("Saída capturada:\n" + output);

        assertTrue(output.contains("Uso:"), "Esperava-se que a saída contivesse 'Uso:'");
    }

    @Test
    @DisplayName("Deve imprimir no console o help sem argumento")
    void shouldGenerateHelpWithEmptyArgs() {
        outContent.reset(); // Limpa buffer antes do teste

        JwtCLI.main(new String[] {});

        System.out.flush(); // Garante que toda saída foi escrita

        String output = outContent.toString();
        System.out.println("Saída capturada:\n" + output);

        assertTrue(output.contains("Uso:"), "Esperava-se que a saída contivesse 'Uso:'");
    }

    @Test
    @DisplayName("Deve gerar um token JWT e imprimir no console")
    void shouldGenerateJWT() {
        // Gera um token
        JwtCLI.main(new String[] { "--generate", "--squad", "DevOps", "--title", "Engineer" });

        // Captura a saída do console
        String output = outContent.toString();
        System.out.println("Saída capturada:\n" + output);

        // Expressão regular para capturar um JWT válido
        Pattern pattern = Pattern.compile("(eyJ[\\w-]+\\.eyJ[\\w-]+\\.[\\w-]+)");
        Matcher matcher = pattern.matcher(output);

        assertTrue(matcher.find(), "Nenhum token JWT foi encontrado na saída.");

        String token = matcher.group(1);
        assertNotNull(token, "O token não deveria ser nulo.");

        System.out.println("Token extraído: " + token);
    }

    @Test
    @DisplayName("Deve gerar e validar um token JWT corretamente")
    void shouldGenerateAndValidateJWT() {

        JwtCLI.main(new String[] { "--generate", "--squad", "teste", "--title", "teste" });
        String output = outContent.toString();
        String token = extractToken(output);
        String secretKey = extractSecretKey(output);

        assertNotNull(token, "O token não deve ser nulo.");
        assertNotNull(secretKey, "A chave secreta não deve ser nula.");

        System.err.println("Output: \n" + output);
        System.err.println("Token: " + token);
        System.err.println("Secret Key: " + secretKey);

        outContent.reset();

        JwtCLI.main(new String[] { "--validate", "--token", token, "--secret", secretKey });
        String validationOutput = outContent.toString();
        System.err.println("Saída da validação:\n" + validationOutput);

        assertTrue(validationOutput.contains("O token não válido"), "Esperava-se que o token fosse validado.");
    }


    // @Test
    // @DisplayName("Deve gerar e restaurar um token JWT corretamente")
    // void shouldGenerateAndRestoreJWT() {

    //     JwtCLI.main(new String[] { "--generate", "--squad", "teste", "--title", "teste" });
    //     String output = outContent.toString();
    //     String token = extractToken(output);
    //     String secretKey = extractSecretKey(output);

    //     assertNotNull(token, "O token não deve ser nulo.");
    //     assertNotNull(secretKey, "A chave secreta não deve ser nula.");

    //     System.err.println(outContent.toString());

    //     outContent.reset();

    //     JwtCLI.main(new String[] { "--restore", "--token", (String) token, "--secret", (String) secretKey });
    //     String validationOutput = outContent.toString();
    //     System.out.println("Saída da validação:\n" + validationOutput);

    //     // assertTrue(validationOutput.contains("O token não válido"), "Esperava-se que o token fosse validado.");
    // }

    private String extractToken(String output) {
        String[] lines = output.split("\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("Token JWT gerado:")) {
                if (i + 1 < lines.length) {
                    return lines[i + 1].replace("[INFO]:", "").trim();
                }
            }
        }
        throw new RuntimeException("Token não encontrado na saída.\nSaída obtida:\n" + output);
    }

    private String extractSecretKey(String output) {
        String[] lines = output.split("\n");
        boolean found = false;

        for (String line : lines) {
            if (line.contains("Recuperando a chave de assinatura:")) {
                found = true;
                continue;
            }

            System.err.println(found);

            if (found) {
                // Remove o prefixo de log e espaços extras
                String keyLine = line.replaceAll(".*\\[INFO\\]:", "").trim();

                System.err.println(keyLine);

                // Remove colchetes caso existam
                keyLine = keyLine.replaceAll("[\\[\\]]", "");

                System.err.println(keyLine);


                // Valida caracteres permitidos em Base64URL
                if (!keyLine.matches("^[A-Za-z0-9_-]+$")) {
                    throw new RuntimeException("Chave de assinatura contém caracteres inválidos: " + keyLine);
                }


                System.err.println(keyLine);


                return keyLine;
            }
        }

        throw new RuntimeException("Chave de assinatura não encontrada na saída.\nSaída obtida:\n" + output);
    }

}
