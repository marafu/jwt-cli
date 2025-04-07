package assis.matheus.artigo.jwt.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}