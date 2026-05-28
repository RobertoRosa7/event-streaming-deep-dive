package br.com.orderflow.eventstreaming.infra.output.memory;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryProcessedEventRepositoryAdapter implements ProcessedEventRepositoryPort {

    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    @Override
    public boolean existsByEventId(String eventId) {
        return processedEventIds.contains(eventId);
    }

    @Override
    public void markAsProcessed(String eventId) {
        processedEventIds.add(eventId);
    }
}
