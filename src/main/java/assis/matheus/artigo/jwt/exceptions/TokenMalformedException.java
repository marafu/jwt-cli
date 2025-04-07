package assis.matheus.artigo.jwt.exceptions;

public class TokenMalformedException extends AuthenticationException {
    public TokenMalformedException(String message, Throwable cause) {
        super(message, cause);
    }
}
