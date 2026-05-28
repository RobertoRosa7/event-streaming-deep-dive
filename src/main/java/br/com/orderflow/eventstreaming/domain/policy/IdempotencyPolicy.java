package br.com.orderflow.eventstreaming.domain.policy;

/**
 * Contrato de política de idempotência para ingestão de eventos.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public interface IdempotencyPolicy {

    /**
     * Verifica se um evento já foi processado anteriormente.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador único do evento.
     * @return true quando o evento já tiver sido processado.
     */
    boolean isDuplicate(String eventId);

    /**
     * Registra um evento como processado na política de idempotência.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador único do evento processado.
     */
    void registerAsProcessed(String eventId);
}
