package br.com.orderflow.eventstreaming.domain.port.output;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

public interface EventPublisherPort {

    void publishOrderEvent(OrderEvent orderEvent);
}
