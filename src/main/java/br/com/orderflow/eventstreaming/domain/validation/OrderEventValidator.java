package br.com.orderflow.eventstreaming.domain.validation;

import br.com.orderflow.eventstreaming.domain.exception.BusinessValidationException;
import br.com.orderflow.eventstreaming.domain.model.OrderEvent;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Componente de validação de eventos de pedido no domínio.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Component
public class OrderEventValidator {

    /**
     * Valida os campos obrigatórios e regras de consistência do evento.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param orderEvent evento de pedido recebido para validação.
     */
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

    /**
     * Verifica se uma string está vazia ou em branco.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param value valor textual a ser verificado.
     * @return true quando o valor for nulo, vazio ou apenas com espaços.
     */
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
