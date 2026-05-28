package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de binding de propriedades de tópicos Kafka.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@Configuration
@EnableConfigurationProperties(OrderFlowTopicsProperties.class)
public class TopicsConfiguration {
}
