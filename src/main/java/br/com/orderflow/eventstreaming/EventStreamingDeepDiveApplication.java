package br.com.orderflow.eventstreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Event Streaming Deep Dive application.
 * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
 * Architectures.
 */
@SpringBootApplication
public class EventStreamingDeepDiveApplication {

    /**
     * Initializes the Spring Boot context to run the application.
     * Book reference: Event Streaming Deep Dive: Kafka and Event-Driven
     * Architectures.
     *
     * @param args command-line arguments received at startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(EventStreamingDeepDiveApplication.class, args);
    }
}
