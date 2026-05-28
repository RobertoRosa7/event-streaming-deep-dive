package br.com.orderflow.eventstreaming.domain.port.input;

import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

public interface IngestOrderEventUseCase {

    IngestionResult ingest(OrderEvent orderEvent);
}
