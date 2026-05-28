package br.com.orderflow.eventstreaming.domain.policy;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * Implementação de política de idempotência baseada em repositório.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Component
public class RepositoryBackedIdempotencyPolicy implements IdempotencyPolicy {

    private final ProcessedEventRepositoryPort processedEventRepositoryPort;

    /**
     * Cria a política de idempotência apoiada por porta de persistência.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param processedEventRepositoryPort porta de consulta e registro de eventos
     *                                     processados.
     */
    public RepositoryBackedIdempotencyPolicy(ProcessedEventRepositoryPort processedEventRepositoryPort) {
        this.processedEventRepositoryPort = processedEventRepositoryPort;
    }

    /**
     * Consulta no repositório se o identificador já foi processado.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador do evento.
     * @return true quando o evento já existir no repositório de processados.
     */
    @Override
    public boolean isDuplicate(String eventId) {
        return processedEventRepositoryPort.existsByEventId(eventId);
    }

    /**
     * Persiste o evento como processado para garantir idempotência futura.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador do evento processado.
     */
    @Override
    public void registerAsProcessed(String eventId) {
        processedEventRepositoryPort.markAsProcessed(eventId);
    }
}
