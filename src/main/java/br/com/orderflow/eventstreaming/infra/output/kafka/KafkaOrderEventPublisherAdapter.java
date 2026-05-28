package br.com.orderflow.eventstreaming.infra.output.kafka;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import br.com.orderflow.eventstreaming.infra.config.OrderFlowTopicsProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderEventPublisherAdapter implements EventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderFlowTopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    public KafkaOrderEventPublisherAdapter(
        KafkaTemplate<String, String> kafkaTemplate,
        OrderFlowTopicsProperties topicsProperties,
        ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicsProperties = topicsProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishOrderEvent(OrderEvent orderEvent) {
        try {
            String payload = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send(topicsProperties.output(), orderEvent.orderId(), payload);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Falha ao serializar evento para publicacao.", ex);
        }
    }
}
