package br.com.orderflow.eventstreaming.domain.exception;

/**
 * Exception for business rule validation in the domain layer.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public class BusinessValidationException extends RuntimeException {

    /**
     * Creates a validation exception with a business message.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param message business rule violation message.
     */
    public BusinessValidationException(String message) {
        super(message);
    }
}
