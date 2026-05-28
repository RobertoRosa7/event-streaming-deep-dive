package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orderflow.topics")
public record OrderFlowTopicsProperties(
    String input,
    String output
) {
}
