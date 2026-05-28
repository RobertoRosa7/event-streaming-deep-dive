package br.com.orderflow.eventstreaming.domain.port.input;

import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

/**
 * Input port for order event ingestion.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
public interface IngestOrderEventUseCase {

    /**
     * Ingests an order event by applying validation and idempotency.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param orderEvent order event received from the edge.
     * @return business result of ingestion.
     */
    IngestionResult ingest(OrderEvent orderEvent);
}
