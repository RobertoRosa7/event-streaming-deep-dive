package br.com.orderflow.eventstreaming.domain.port.output;

import br.com.orderflow.eventstreaming.domain.model.OrderEvent;

/**
 * Porta de saída responsável pela publicação de eventos processados.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public interface EventPublisherPort {

    /**
     * Publica um evento de pedido em canal externo.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEvent evento de pedido a ser publicado.
     */
    void publishOrderEvent(OrderEvent orderEvent);
}
