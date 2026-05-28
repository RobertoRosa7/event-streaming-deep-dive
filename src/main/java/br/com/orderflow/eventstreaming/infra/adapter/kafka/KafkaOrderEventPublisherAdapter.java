package br.com.orderflow.eventstreaming.infra.adapter.kafka;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import br.com.orderflow.eventstreaming.infra.config.OrderFlowTopicsProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka outbound adapter for publishing processed events.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Component
public class KafkaOrderEventPublisherAdapter implements EventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderFlowTopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    /**
     * Creates the Kafka publishing adapter.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param kafkaTemplate    Kafka client for sending messages.
     * @param topicsProperties application topic properties.
     * @param objectMapper     JSON serializer.
     */
    public KafkaOrderEventPublisherAdapter(
            KafkaTemplate<String, String> kafkaTemplate,
            OrderFlowTopicsProperties topicsProperties,
            ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * Serializes and publishes the event to the output topic.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEvent order event to be published.
     */
    @Override
    public void publishOrderEvent(OrderEvent orderEvent) {
        try {
            String payload = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send(topicsProperties.output(), orderEvent.orderId(), payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize event for publication.", ex);
        }
    }
}
