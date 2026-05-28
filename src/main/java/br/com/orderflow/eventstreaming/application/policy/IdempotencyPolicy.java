package br.com.orderflow.eventstreaming.application.policy;

public interface IdempotencyPolicy {

    boolean isDuplicate(String eventId);

    void registerAsProcessed(String eventId);
}
