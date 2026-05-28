package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades dos tópicos Kafka utilizados pela aplicação.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 *
 * @param input  nome do tópico de entrada de eventos brutos.
 * @param output nome do tópico de saída de eventos curados.
 */
@ConfigurationProperties(prefix = "orderflow.topics")
public record OrderFlowTopicsProperties(
        String input,
        String output) {
}
