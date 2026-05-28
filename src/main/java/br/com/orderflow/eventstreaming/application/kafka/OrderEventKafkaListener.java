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
 * Adaptador de entrada Kafka responsável por receber eventos brutos de pedido.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Component
public class OrderEventKafkaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventKafkaListener.class);

    private final ObjectMapper objectMapper;
    private final IngestOrderEventUseCase ingestOrderEventUseCase;

    /**
     * Cria o listener Kafka com desserialização JSON e encaminhamento para o caso
     * de uso.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param objectMapper            serializador e desserializador JSON.
     * @param ingestOrderEventUseCase porta de entrada para ingestão de eventos.
     */
    public OrderEventKafkaListener(ObjectMapper objectMapper, IngestOrderEventUseCase ingestOrderEventUseCase) {
        this.objectMapper = objectMapper;
        this.ingestOrderEventUseCase = ingestOrderEventUseCase;
    }

    /**
     * Consome registros do tópico de entrada e aciona a ingestão do evento no
     * domínio.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param record registro Kafka contendo o payload do evento de pedido.
     */
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
