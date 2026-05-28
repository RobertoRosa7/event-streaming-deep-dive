package br.com.orderflow.eventstreaming.application.usecase;

import br.com.orderflow.eventstreaming.application.policy.IdempotencyPolicy;
import br.com.orderflow.eventstreaming.application.validation.OrderEventValidator;
import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.IngestionStatus;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.input.IngestOrderEventUseCase;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import org.springframework.stereotype.Service;

@Service
public class IngestOrderEventService implements IngestOrderEventUseCase {

    private final OrderEventValidator orderEventValidator;
    private final IdempotencyPolicy idempotencyPolicy;
    private final EventPublisherPort eventPublisherPort;

    public IngestOrderEventService(
        OrderEventValidator orderEventValidator,
        IdempotencyPolicy idempotencyPolicy,
        EventPublisherPort eventPublisherPort
    ) {
        this.orderEventValidator = orderEventValidator;
        this.idempotencyPolicy = idempotencyPolicy;
        this.eventPublisherPort = eventPublisherPort;
    }

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
