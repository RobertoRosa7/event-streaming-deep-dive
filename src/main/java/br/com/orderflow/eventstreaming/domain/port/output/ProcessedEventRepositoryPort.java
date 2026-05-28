package br.com.orderflow.eventstreaming.domain.port.output;

/**
 * Output port for storing and querying processed events.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public interface ProcessedEventRepositoryPort {

    /**
     * Checks whether the event has already been recorded as processed.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId event identifier.
     * @return true when the event is already recorded.
     */
    boolean existsByEventId(String eventId);

    /**
     * Marks the event as processed in the repository.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId identifier of the processed event.
     */
    void markAsProcessed(String eventId);
}
