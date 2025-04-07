package assis.matheus.artigo.jwt.valueobjects;

public class Squad {
    private String value;

    public Squad(String value) {
        this.validate(value);
        this.value = value;
    }

    private void validate (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O valor do Squad não pode ser nulo ou vazio.");
        }
        if (value.length() < 3 || value.length() > 50 ) {
            throw new IllegalArgumentException("O valor do Squad não pode ser menor que 3 e exceder 50 caracteres.");
        }
    }

    public String getValue() {
        return this.value;
    }
}
