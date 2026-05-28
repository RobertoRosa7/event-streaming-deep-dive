package br.com.orderflow.eventstreaming.domain.validation;

import br.com.orderflow.eventstreaming.domain.exception.BusinessValidationException;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Domain validation component for order events.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Component
public class OrderEventValidator {

    /**
     * Validates required fields and event consistency rules.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEvent order event received for validation.
     */
    public void validate(OrderEvent orderEvent) {
        if (orderEvent == null) {
            throw new BusinessValidationException("Order event cannot be null.");
        }
        if (isBlank(orderEvent.eventId())) {
            throw new BusinessValidationException("eventId is required.");
        }
        if (isBlank(orderEvent.orderId())) {
            throw new BusinessValidationException("orderId is required.");
        }
        if (isBlank(orderEvent.customerId())) {
            throw new BusinessValidationException("customerId is required.");
        }
        if (orderEvent.totalAmount() == null || orderEvent.totalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessValidationException("totalAmount must be greater than zero.");
        }
        if (orderEvent.occurredAt() == null) {
            throw new BusinessValidationException("occurredAt is required.");
        }
    }

    /**
     * Checks whether a string is empty or blank.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param value text value to be checked.
     * @return true when the value is null, empty, or only whitespace.
     */
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
