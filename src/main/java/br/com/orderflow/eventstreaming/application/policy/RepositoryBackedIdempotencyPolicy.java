package br.com.orderflow.eventstreaming.application.policy;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class RepositoryBackedIdempotencyPolicy implements IdempotencyPolicy {

    private final ProcessedEventRepositoryPort processedEventRepositoryPort;

    public RepositoryBackedIdempotencyPolicy(ProcessedEventRepositoryPort processedEventRepositoryPort) {
        this.processedEventRepositoryPort = processedEventRepositoryPort;
    }

    @Override
    public boolean isDuplicate(String eventId) {
        return processedEventRepositoryPort.existsByEventId(eventId);
    }

    @Override
    public void registerAsProcessed(String eventId) {
        processedEventRepositoryPort.markAsProcessed(eventId);
    }
}
