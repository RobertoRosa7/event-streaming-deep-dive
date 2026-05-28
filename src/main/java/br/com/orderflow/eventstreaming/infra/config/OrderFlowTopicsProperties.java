package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for Kafka topics used by the application.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 *
 * @param input  name of the input topic for raw events.
 * @param output name of the output topic for curated events.
 */
@ConfigurationProperties(prefix = "orderflow.topics")
public record OrderFlowTopicsProperties(
                String input,
                String output) {
}
