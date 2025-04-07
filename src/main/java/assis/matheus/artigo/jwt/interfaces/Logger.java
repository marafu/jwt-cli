package assis.matheus.artigo.jwt.interfaces;

public interface Logger {
    public void debug(String message);
    public void info(String message);
    public void error(String message, Exception exception);
    public void error(String message);
}