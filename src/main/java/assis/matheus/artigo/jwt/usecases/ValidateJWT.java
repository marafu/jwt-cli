package assis.matheus.artigo.jwt.usecases;


import assis.matheus.artigo.jwt.exceptions.AuthenticationException;
import assis.matheus.artigo.jwt.interfaces.JWT;
import assis.matheus.artigo.jwt.interfaces.Logger;

public class ValidateJWT {
    private Logger logger;
    private JWT jwt;

    public ValidateJWT(JWT jwt, Logger logger) {
        this.logger = logger;
        this.jwt = jwt;
    }
    
    public void execute(String token, String secret) {

        try {
            this.jwt.validate(token);
            logger.info("O token é válido");
        } catch (AuthenticationException e) {
            logger.info("O token não válido.");
        }
    }
}
