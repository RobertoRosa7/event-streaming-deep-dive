package br.com.orderflow.eventstreaming.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Domain event that represents an order occurrence in OrderFlow.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 *
 * @param eventId     unique identifier for the event.
 * @param orderId     business order identifier.
 * @param customerId  identifier of the customer associated with the order.
 * @param totalAmount total monetary value of the order.
 * @param occurredAt  timestamp when the event occurred at the source.
 */
public record OrderEvent(
                String eventId,
                String orderId,
                String customerId,
                BigDecimal totalAmount,
                Instant occurredAt) {
}
