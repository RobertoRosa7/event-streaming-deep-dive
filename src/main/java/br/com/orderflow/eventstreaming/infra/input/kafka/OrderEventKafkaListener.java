package br.com.orderflow.eventstreaming.infra.input.kafka;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.input.IngestOrderEventUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventKafkaListener.class);

    private final ObjectMapper objectMapper;
    private final IngestOrderEventUseCase ingestOrderEventUseCase;

    public OrderEventKafkaListener(ObjectMapper objectMapper, IngestOrderEventUseCase ingestOrderEventUseCase) {
        this.objectMapper = objectMapper;
        this.ingestOrderEventUseCase = ingestOrderEventUseCase;
    }

    @KafkaListener(topics = "${orderflow.topics.input}")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            OrderEvent orderEvent = objectMapper.readValue(record.value(), OrderEvent.class);
            ingestOrderEventUseCase.ingest(orderEvent);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Falha ao desserializar evento recebido do topico {}.", record.topic(), ex);
        }
    }
}
