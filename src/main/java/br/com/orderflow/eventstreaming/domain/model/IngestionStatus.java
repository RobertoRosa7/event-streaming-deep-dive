package br.com.orderflow.eventstreaming.domain.model;

/**
 * Enumera os estados possíveis para a ingestão de eventos.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
public enum IngestionStatus {
    /**
     * Evento processado com sucesso.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     */
    PROCESSED,

    /**
     * Evento identificado como duplicado e ignorado.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     */
    DUPLICATED
}
