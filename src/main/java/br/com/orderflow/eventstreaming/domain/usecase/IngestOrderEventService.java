package br.com.orderflow.eventstreaming.domain.usecase;

import br.com.orderflow.eventstreaming.domain.policy.IdempotencyPolicy;
import br.com.orderflow.eventstreaming.domain.validation.OrderEventValidator;
import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.IngestionStatus;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.input.IngestOrderEventUseCase;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import org.springframework.stereotype.Service;

/**
 * Implementação do caso de uso de ingestão de eventos de pedido.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Service
public class IngestOrderEventService implements IngestOrderEventUseCase {

    private final OrderEventValidator orderEventValidator;
    private final IdempotencyPolicy idempotencyPolicy;
    private final EventPublisherPort eventPublisherPort;

    /**
     * Cria o serviço de ingestão com validação, política de idempotência e
     * publicação.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEventValidator validador de regras de negócio do evento.
     * @param idempotencyPolicy   política para controle de duplicidade.
     * @param eventPublisherPort  porta de saída para publicação do evento.
     */
    public IngestOrderEventService(
            OrderEventValidator orderEventValidator,
            IdempotencyPolicy idempotencyPolicy,
            EventPublisherPort eventPublisherPort) {
        this.orderEventValidator = orderEventValidator;
        this.idempotencyPolicy = idempotencyPolicy;
        this.eventPublisherPort = eventPublisherPort;
    }

    /**
     * Executa a ingestão de evento aplicando validação e idempotência antes da
     * publicação.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEvent evento recebido para processamento.
     * @return resultado do processamento com status e mensagem funcional.
     */
    @Override
    public IngestionResult ingest(OrderEvent orderEvent) {
        orderEventValidator.validate(orderEvent);

        if (idempotencyPolicy.isDuplicate(orderEvent.eventId())) {
            return new IngestionResult(IngestionStatus.DUPLICATED, "Evento duplicado ignorado.");
        }

        eventPublisherPort.publishOrderEvent(orderEvent);
        idempotencyPolicy.registerAsProcessed(orderEvent.eventId());
        return new IngestionResult(IngestionStatus.PROCESSED, "Evento processado com sucesso.");
    }
}
