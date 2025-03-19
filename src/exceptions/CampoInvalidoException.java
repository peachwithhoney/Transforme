package exceptions;

public class CampoInvalidoException extends Exception {

    public CampoInvalidoException(String message) {
        super(message);
    }

    public CampoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}