package assis.matheus.artigo.jwt.exceptions;

public class TokenUnsupportedException extends AuthenticationException {
    public TokenUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}
