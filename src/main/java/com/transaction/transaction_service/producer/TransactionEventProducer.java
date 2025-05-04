package com.transaction.transaction_service.producer;


import com.transaction.transaction_service.event.TransactionCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventProducer {

    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionCreatedEvent(TransactionCreatedEvent event) {
        kafkaTemplate.send("transaction-events", event);
    }
}