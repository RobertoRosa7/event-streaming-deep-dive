package br.com.orderflow.eventstreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação Event Streaming Deep Dive.
 * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
 */
@SpringBootApplication
public class EventStreamingDeepDiveApplication {

    /**
     * Inicializa o contexto Spring Boot para execução da aplicação.
     * Referência do livro: Event Streaming Deep Dive: Kafka e Arquiteturas Orientadas a Eventos.
     *
     * @param args argumentos de linha de comando recebidos na inicialização.
     */
    public static void main(String[] args) {
        SpringApplication.run(EventStreamingDeepDiveApplication.class, args);
    }
}
