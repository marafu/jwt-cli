package assis.matheus.artigo.jwt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import assis.matheus.artigo.jwt.interfaces.Logger;


public class SoutLogger implements Logger {
    private static final String DEBUG = "DEBUG";
    private static final String INFO = "INFO";
    private static final String ERROR = "ERROR";

    public void debug(String message) {
        log(DEBUG, message);
    }

 
    public void info(String message) {
        log(INFO, message);
    }

    public void error(String message, Exception exception) {
        log(ERROR, message);
        if (exception != null) {
            System.out.println(formatMessage(ERROR, "Stacktrace:"));
            exception.printStackTrace(System.out);
        }
    }

    public void error(String message) {
        log(ERROR, message);
    }

    private static void log(String level, String message) {
        System.out.println(formatMessage(level, message));
    }

    private static String formatMessage(String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format("[%s] [%s]: %s", timestamp, level, message);
    }
}
