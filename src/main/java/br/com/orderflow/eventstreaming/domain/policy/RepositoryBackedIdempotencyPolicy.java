package br.com.orderflow.eventstreaming.domain.policy;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import org.springframework.stereotype.Component;

/**
 * Repository-backed idempotency policy implementation.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Component
public class RepositoryBackedIdempotencyPolicy implements IdempotencyPolicy {

    private final ProcessedEventRepositoryPort processedEventRepositoryPort;

    /**
     * Creates the idempotency policy backed by a persistence port.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param processedEventRepositoryPort port for querying and recording processed
     *                                     events.
     */
    public RepositoryBackedIdempotencyPolicy(ProcessedEventRepositoryPort processedEventRepositoryPort) {
        this.processedEventRepositoryPort = processedEventRepositoryPort;
    }

    /**
     * Checks in the repository whether the identifier has already been processed.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId event identifier.
     * @return true when the event already exists in the processed-events
     *         repository.
     */
    @Override
    public boolean isDuplicate(String eventId) {
        return processedEventRepositoryPort.existsByEventId(eventId);
    }

    /**
     * Persists the event as processed to guarantee future idempotency.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId identifier of the processed event.
     */
    @Override
    public void registerAsProcessed(String eventId) {
        processedEventRepositoryPort.markAsProcessed(eventId);
    }
}
