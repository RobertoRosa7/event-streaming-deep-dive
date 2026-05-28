package br.com.orderflow.eventstreaming.domain.port.output;

/**
 * Porta de saída para armazenamento e consulta de eventos processados.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public interface ProcessedEventRepositoryPort {

    /**
     * Verifica se o evento já foi registrado como processado.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador do evento.
     * @return true quando o evento já estiver registrado.
     */
    boolean existsByEventId(String eventId);

    /**
     * Marca o evento como processado no repositório.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador do evento processado.
     */
    void markAsProcessed(String eventId);
}
