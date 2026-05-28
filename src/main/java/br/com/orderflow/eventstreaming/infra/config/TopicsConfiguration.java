package br.com.orderflow.eventstreaming.infra.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OrderFlowTopicsProperties.class)
public class TopicsConfiguration {
}
