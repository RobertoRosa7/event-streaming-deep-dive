package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Kafka topic properties binding.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@Configuration
@EnableConfigurationProperties(OrderFlowTopicsProperties.class)
public class TopicsConfiguration {
}
