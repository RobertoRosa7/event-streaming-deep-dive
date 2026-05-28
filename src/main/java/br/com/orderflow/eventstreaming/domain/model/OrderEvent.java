package br.com.orderflow.eventstreaming.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Evento de domínio que representa a ocorrência de pedido na OrderFlow.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 *
 * @param eventId     identificador único do evento.
 * @param orderId     identificador do pedido de negócio.
 * @param customerId  identificador do cliente associado ao pedido.
 * @param totalAmount valor total monetário do pedido.
 * @param occurredAt  instante em que o evento ocorreu na origem.
 */
public record OrderEvent(
        String eventId,
        String orderId,
        String customerId,
        BigDecimal totalAmount,
        Instant occurredAt) {
}
