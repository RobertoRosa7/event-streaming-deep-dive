package br.com.orderflow.eventstreaming.domain.model;

/**
 * Result of ingesting an order event.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 *
 * @param status  final status of event processing.
 * @param message business message for the ingestion result.
 */
public record IngestionResult(
                IngestionStatus status,
                String message) {
}
