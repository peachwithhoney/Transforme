package exceptions;

public class CampoObrigatorioException extends Exception {
    public CampoObrigatorioException(String mensagem) {
        super(mensagem);
    }
}