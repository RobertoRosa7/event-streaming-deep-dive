package br.com.orderflow.eventstreaming.infra.adapter.memory;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * In-memory outbound adapter for processed-event control.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Component
public class InMemoryProcessedEventRepositoryAdapter implements ProcessedEventRepositoryPort {

    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    /**
     * Checks in memory whether an event has already been processed.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId unique event identifier.
     * @return true when the event exists in the processed set.
     */
    @Override
    public boolean existsByEventId(String eventId) {
        return processedEventIds.contains(eventId);
    }

    /**
     * Registers an event as processed in memory.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param eventId unique identifier of the processed event.
     */
    @Override
    public void markAsProcessed(String eventId) {
        processedEventIds.add(eventId);
    }
}
