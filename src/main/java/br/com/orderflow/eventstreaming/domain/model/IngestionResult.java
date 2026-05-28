package br.com.orderflow.eventstreaming.domain.model;

/**
 * Resultado da ingestão de um evento de pedido.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 *
 * @param status  status final do processamento do evento.
 * @param message mensagem funcional do resultado da ingestão.
 */
public record IngestionResult(
        IngestionStatus status,
        String message) {
}
