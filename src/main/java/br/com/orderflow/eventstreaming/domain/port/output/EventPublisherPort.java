package br.com.orderflow.eventstreaming.domain.port.output;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

/**
 * Output port responsible for publishing processed events.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public interface EventPublisherPort {

    /**
     * Publishes an order event to an external channel.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEvent order event to be published.
     */
    void publishOrderEvent(OrderEvent orderEvent);
}
