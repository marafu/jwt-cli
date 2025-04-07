package assis.matheus.artigo.jwt.exceptions;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
