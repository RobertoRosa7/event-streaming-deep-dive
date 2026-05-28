package br.com.orderflow.eventstreaming.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderEvent(
    String eventId,
    String orderId,
    String customerId,
    BigDecimal totalAmount,
    Instant occurredAt
) {
}
