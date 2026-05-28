package br.com.orderflow.eventstreaming.infra.adapter.memory;

import br.com.orderflow.eventstreaming.domain.port.output.ProcessedEventRepositoryPort;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Adaptador de saída em memória para controle de eventos processados.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Component
public class InMemoryProcessedEventRepositoryAdapter implements ProcessedEventRepositoryPort {

    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    /**
     * Consulta em memória se um evento já foi processado.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador único do evento.
     * @return true quando o evento existir no conjunto de processados.
     */
    @Override
    public boolean existsByEventId(String eventId) {
        return processedEventIds.contains(eventId);
    }

    /**
     * Registra em memória um evento como processado.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param eventId identificador único do evento processado.
     */
    @Override
    public void markAsProcessed(String eventId) {
        processedEventIds.add(eventId);
    }
}
