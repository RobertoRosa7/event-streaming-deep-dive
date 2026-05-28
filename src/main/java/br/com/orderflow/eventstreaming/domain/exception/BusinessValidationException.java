package br.com.orderflow.eventstreaming.domain.exception;

/**
 * Exceção de validação de regras de negócio na camada de domínio.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public class BusinessValidationException extends RuntimeException {

    /**
     * Cria uma exceção de validação com mensagem funcional.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param message mensagem da violação de regra de negócio.
     */
    public BusinessValidationException(String message) {
        super(message);
    }
}
