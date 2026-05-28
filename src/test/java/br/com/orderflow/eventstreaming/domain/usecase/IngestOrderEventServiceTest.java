package br.com.orderflow.eventstreaming.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.orderflow.eventstreaming.domain.exception.BusinessValidationException;
import br.com.orderflow.eventstreaming.domain.policy.IdempotencyPolicy;
import br.com.orderflow.eventstreaming.domain.validation.OrderEventValidator;
import br.com.orderflow.eventstreaming.domain.model.IngestionResult;
import br.com.orderflow.eventstreaming.domain.model.IngestionStatus;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import br.com.orderflow.eventstreaming.domain.port.output.EventPublisherPort;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the order event ingestion use case.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
class IngestOrderEventServiceTest {

    @Mock
    private IdempotencyPolicy idempotencyPolicy;

    @Mock
    private EventPublisherPort eventPublisherPort;

    private IngestOrderEventService ingestOrderEventService;

    /**
     * Configures mocked dependencies for each test scenario.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingestOrderEventService = new IngestOrderEventService(new OrderEventValidator(), idempotencyPolicy,
                eventPublisherPort);
    }

    /**
     * Ensures processing and publication when the event is not duplicated.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    @Test
    void shouldProcessValidEventWhenNotDuplicated() {
        OrderEvent orderEvent = validOrderEvent();
        when(idempotencyPolicy.isDuplicate(orderEvent.eventId())).thenReturn(false);

        IngestionResult result = ingestOrderEventService.ingest(orderEvent);

        assertEquals(IngestionStatus.PROCESSED, result.status());
        verify(eventPublisherPort).publishOrderEvent(orderEvent);
        verify(idempotencyPolicy).registerAsProcessed(orderEvent.eventId());
    }

    /**
     * Ensures duplicated events are not republished.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    @Test
    void shouldIgnoreDuplicatedEvent() {
        OrderEvent orderEvent = validOrderEvent();
        when(idempotencyPolicy.isDuplicate(orderEvent.eventId())).thenReturn(true);

        IngestionResult result = ingestOrderEventService.ingest(orderEvent);

        assertEquals(IngestionStatus.DUPLICATED, result.status());
        verify(eventPublisherPort, never()).publishOrderEvent(orderEvent);
        verify(idempotencyPolicy, never()).registerAsProcessed(orderEvent.eventId());
    }

    /**
     * Ensures validation fails for an event with an invalid amount.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     */
    @Test
    void shouldThrowValidationExceptionWhenAmountIsInvalid() {
        OrderEvent invalidEvent = new OrderEvent("evt-01", "ord-01", "cst-01", BigDecimal.ZERO, Instant.now());

        assertThrows(BusinessValidationException.class, () -> ingestOrderEventService.ingest(invalidEvent));
        verify(eventPublisherPort, never()).publishOrderEvent(invalidEvent);
    }

    /**
     * Creates a valid event for reuse across test scenarios.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @return consistent order event for unit tests.
     */
    private OrderEvent validOrderEvent() {
        return new OrderEvent(
                "evt-01",
                "ord-01",
                "cst-01",
                new BigDecimal("199.90"),
                Instant.now());
    }
}
