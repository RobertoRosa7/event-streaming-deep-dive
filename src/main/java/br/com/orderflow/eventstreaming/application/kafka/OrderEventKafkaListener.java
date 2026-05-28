package br.com.orderflow.eventstreaming.application.kafka;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.input.IngestOrderEventUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka inbound adapter responsible for receiving raw order events.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Component
public class OrderEventKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventKafkaListener.class);

    private final ObjectMapper objectMapper;
    private final IngestOrderEventUseCase ingestOrderEventUseCase;

    /**
     * Creates the Kafka listener with JSON deserialization and forwarding to the
     * use case.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param objectMapper            JSON serializer and deserializer.
     * @param ingestOrderEventUseCase input port for event ingestion.
     */
    public OrderEventKafkaListener(ObjectMapper objectMapper, IngestOrderEventUseCase ingestOrderEventUseCase) {
        this.objectMapper = objectMapper;
        this.ingestOrderEventUseCase = ingestOrderEventUseCase;
    }

    /**
     * Consumes records from the input topic and triggers event ingestion in the
     * domain.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param record Kafka record containing the order event payload.
     */
    @KafkaListener(topics = "${orderflow.topics.input}")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(record.value(), OrderEvent.class);
            ingestOrderEventUseCase.ingest(orderEvent);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Failed to deserialize event received from topic {}.", record.topic(), ex);
        }
    }
}
