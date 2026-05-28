package br.com.orderflow.eventstreaming.domain.model;

public record IngestionResult(
    IngestionStatus status,
    String message
) {
}
