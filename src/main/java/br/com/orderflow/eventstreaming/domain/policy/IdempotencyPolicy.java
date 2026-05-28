package br.com.orderflow.eventstreaming.domain.policy;

/**
 * Idempotency policy contract for event ingestion.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public interface IdempotencyPolicy {

    /**
     * Checks whether an event has already been processed.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId unique identifier for the event.
     * @return true when the event has already been processed.
     */
    boolean isDuplicate(String eventId);

    /**
     * Registers an event as processed in the idempotency policy.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId unique identifier for the processed event.
     */
    void registerAsProcessed(String eventId);
}
