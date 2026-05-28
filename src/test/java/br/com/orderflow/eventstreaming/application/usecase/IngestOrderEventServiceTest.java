package br.com.orderflow.eventstreaming.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.orderflow.eventstreaming.application.exception.BusinessValidationException;
import br.com.orderflow.eventstreaming.application.policy.IdempotencyPolicy;
import br.com.orderflow.eventstreaming.application.validation.OrderEventValidator;
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

class IngestOrderEventServiceTest {

    @Mock
    private IdempotencyPolicy idempotencyPolicy;

    @Mock
    private EventPublisherPort eventPublisherPort;

    private IngestOrderEventService ingestOrderEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingestOrderEventService = new IngestOrderEventService(new OrderEventValidator(), idempotencyPolicy, eventPublisherPort);
    }

    @Test
    void shouldProcessValidEventWhenNotDuplicated() {
        OrderEvent orderEvent = validOrderEvent();
        when(idempotencyPolicy.isDuplicate(orderEvent.eventId())).thenReturn(false);

        IngestionResult result = ingestOrderEventService.ingest(orderEvent);

        assertEquals(IngestionStatus.PROCESSED, result.status());
        verify(eventPublisherPort).publishOrderEvent(orderEvent);
        verify(idempotencyPolicy).registerAsProcessed(orderEvent.eventId());
    }

    @Test
    void shouldIgnoreDuplicatedEvent() {
        OrderEvent orderEvent = validOrderEvent();
        when(idempotencyPolicy.isDuplicate(orderEvent.eventId())).thenReturn(true);

        IngestionResult result = ingestOrderEventService.ingest(orderEvent);

        assertEquals(IngestionStatus.DUPLICATED, result.status());
        verify(eventPublisherPort, never()).publishOrderEvent(orderEvent);
        verify(idempotencyPolicy, never()).registerAsProcessed(orderEvent.eventId());
    }

    @Test
    void shouldThrowValidationExceptionWhenAmountIsInvalid() {
        OrderEvent invalidEvent = new OrderEvent("evt-01", "ord-01", "cst-01", BigDecimal.ZERO, Instant.now());

        assertThrows(BusinessValidationException.class, () -> ingestOrderEventService.ingest(invalidEvent));
        verify(eventPublisherPort, never()).publishOrderEvent(invalidEvent);
    }

    private OrderEvent validOrderEvent() {
        return new OrderEvent(
            "evt-01",
            "ord-01",
            "cst-01",
            new BigDecimal("199.90"),
            Instant.now()
        );
    }
}
