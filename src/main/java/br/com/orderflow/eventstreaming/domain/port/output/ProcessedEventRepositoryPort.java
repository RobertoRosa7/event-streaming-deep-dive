package br.com.orderflow.eventstreaming.domain.port.output;

public interface ProcessedEventRepositoryPort {

    boolean existsByEventId(String eventId);

    void markAsProcessed(String eventId);
}
