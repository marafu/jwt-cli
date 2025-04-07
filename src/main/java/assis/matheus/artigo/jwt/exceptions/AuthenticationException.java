package assis.matheus.artigo.jwt.exceptions;

public class AuthenticationException extends DomainException {
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}