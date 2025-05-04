package com.transaction.transaction_service.event;

import lombok.Data;

@Data
public class OrderStatusUpdatedEvent {
    private String orderId;
    private String newStatus;
}
