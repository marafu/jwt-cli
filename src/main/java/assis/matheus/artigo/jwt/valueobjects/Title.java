package assis.matheus.artigo.jwt.valueobjects;

public class Title {
    private String value;

    public Title(String value) {
        this.value = this.sanitize(value);
    }

    private String sanitize (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O valor do título não pode ser nulo ou vazio.");
        }
        if (value.length() < 3 || value.length() > 350 ) {
            throw new IllegalArgumentException("O valor do título não pode ser menor que 3 e exceder 50 caracteres.");
        }
        return value
                .replace("'", "&#39;")     
                .replace("\"", "&quot;")  
                .replace(";", "&#59;")    
                .replace("--", "&#45;&#45;")
                .replace("<", "&lt;")     
                .replace(">", "&gt;")     
                .replace("(", "&#40;")    
                .replace(")", "&#41;");
    }

    public String getValue() {
        return this.value;
    }
}
