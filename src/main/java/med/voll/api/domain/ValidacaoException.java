package med.voll.api.domain;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem); //construtor da classe mãe "super".
    }
}
