package assis.matheus.artigo.jwt.interfaces;

import javax.crypto.SecretKey;

import assis.matheus.artigo.jwt.exceptions.AuthenticationException;

public interface JWT {
    public String generate(String value) throws AuthenticationException;
    public void validate(String token) throws AuthenticationException;
    public String restore(String token) throws AuthenticationException;
    public String getSigningKey();
    public String getSigningAlgorithm();
    public SecretKey getRawKey();
}
