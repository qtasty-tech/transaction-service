package com.transaction.transaction_service.event;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransactionCreatedEvent {
    private String transactionId;
    private String userId;
    private double totalAmount;
    private Date transactionDate;
    private List<String> orderIds;
}