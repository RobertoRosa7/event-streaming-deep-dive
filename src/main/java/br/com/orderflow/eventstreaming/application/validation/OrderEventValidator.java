package br.com.orderflow.eventstreaming.application.validation;

import br.com.orderflow.eventstreaming.application.exception.BusinessValidationException;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class OrderEventValidator {

    public void validate(OrderEvent orderEvent) {
        if (orderEvent == null) {
            throw new BusinessValidationException("Evento de pedido nao pode ser nulo.");
        }
        if (isBlank(orderEvent.eventId())) {
            throw new BusinessValidationException("eventId e obrigatorio.");
        }
        if (isBlank(orderEvent.orderId())) {
            throw new BusinessValidationException("orderId e obrigatorio.");
        }
        if (isBlank(orderEvent.customerId())) {
            throw new BusinessValidationException("customerId e obrigatorio.");
        }
        if (orderEvent.totalAmount() == null || orderEvent.totalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessValidationException("totalAmount deve ser maior que zero.");
        }
        if (orderEvent.occurredAt() == null) {
            throw new BusinessValidationException("occurredAt e obrigatorio.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
