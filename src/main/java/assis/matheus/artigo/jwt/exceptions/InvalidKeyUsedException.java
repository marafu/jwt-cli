package assis.matheus.artigo.jwt.exceptions;

public class InvalidKeyUsedException extends AuthenticationException {
    public InvalidKeyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}
