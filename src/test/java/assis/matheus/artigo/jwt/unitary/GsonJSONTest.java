package assis.matheus.artigo.jwt.unitary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import assis.matheus.artigo.jwt.implementations.GsonJSON;
import assis.matheus.artigo.jwt.interfaces.JSON;

class Dummy {
    String field = "value";
}

class GsonJSONTest {
    private JSON json;

    @BeforeEach
    @DisplayName("Inicializa a instância de GsonJSON para testes")
    void setUp() {
        json = new GsonJSON();
    }

    @Test
    @DisplayName("Deve serializar corretamente um objeto para JSON")
    void shouldSerializeObjectToJson() {
        

        String expectedJson = "{\"field\":\"value\"}";
        String actualJson = json.create(new Dummy());

        System.out.println();

        assertEquals(expectedJson, actualJson, "A conversão para JSON deve ser precisa");
    }
}
