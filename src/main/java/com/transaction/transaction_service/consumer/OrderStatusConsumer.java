package com.transaction.transaction_service.consumer;


import com.transaction.transaction_service.event.OrderStatusUpdatedEvent;
import com.transaction.transaction_service.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusConsumer {

    private final TransactionService transactionService;

    public OrderStatusConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "order-status-events", groupId = "transaction-group")
    public void handleOrderStatusUpdate(OrderStatusUpdatedEvent event) {
        transactionService.updateOrderStatus(event.getOrderId(), event.getNewStatus());
    }
}