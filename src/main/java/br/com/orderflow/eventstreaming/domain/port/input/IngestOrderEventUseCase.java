package br.com.orderflow.eventstreaming.domain.port.input;

import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

/**
 * Porta de entrada para ingestão de eventos de pedido.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public interface IngestOrderEventUseCase {

    /**
     * Ingesta um evento de pedido aplicando validação e idempotência.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEvent evento de pedido recebido da borda.
     * @return resultado funcional da ingestão.
     */
    IngestionResult ingest(OrderEvent orderEvent);
}
