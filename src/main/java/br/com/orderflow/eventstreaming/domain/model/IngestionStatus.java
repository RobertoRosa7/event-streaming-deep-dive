package br.com.orderflow.eventstreaming.domain.model;

/**
 * Enumerates possible states for event ingestion.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public enum IngestionStatus {
    /**
     * Event processed successfully.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    PROCESSED,

    /**
     * Event identified as duplicated and ignored.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    DUPLICATED
}
