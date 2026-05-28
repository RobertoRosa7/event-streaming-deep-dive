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
 * Implementation of the order event ingestion use case.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Service
public class IngestOrderEventService implements IngestOrderEventUseCase {

    private final OrderEventValidator orderEventValidator;
    private final IdempotencyPolicy idempotencyPolicy;
    private final EventPublisherPort eventPublisherPort;

    /**
     * Creates the ingestion service with validation, idempotency policy, and
     * publication.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEventValidator event business-rule validator.
     * @param idempotencyPolicy   policy for duplicate control.
     * @param eventPublisherPort  output port for event publication.
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
     * Executes event ingestion by applying validation and idempotency before
     * publication.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEvent event received for processing.
     * @return processing result with status and business message.
     */
    @Override
    public IngestionResult ingest(OrderEvent orderEvent) {
        orderEventValidator.validate(orderEvent);

        if (idempotencyPolicy.isDuplicate(orderEvent.eventId())) {
            return new IngestionResult(IngestionStatus.DUPLICATED, "Duplicate event ignored.");
        }

        eventPublisherPort.publishOrderEvent(orderEvent);
        idempotencyPolicy.registerAsProcessed(orderEvent.eventId());
        return new IngestionResult(IngestionStatus.PROCESSED, "Event processed successfully.");
    }
}
