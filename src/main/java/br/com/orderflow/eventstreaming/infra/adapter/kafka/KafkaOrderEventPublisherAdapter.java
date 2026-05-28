package br.com.orderflow.eventstreaming.infra.adapter.kafka;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import br.com.orderflow.eventstreaming.infra.config.OrderFlowTopicsProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Adaptador de saída Kafka para publicação de eventos processados.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Component
public class KafkaOrderEventPublisherAdapter implements EventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderFlowTopicsProperties topicsProperties;
    private final ObjectMapper objectMapper;

    /**
     * Cria o adaptador de publicação Kafka.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param kafkaTemplate    cliente Kafka para envio de mensagens.
     * @param topicsProperties propriedades dos tópicos da aplicação.
     * @param objectMapper     serializador JSON.
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
     * Serializa e publica o evento no tópico de saída.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEvent evento de pedido a ser publicado.
     */
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
