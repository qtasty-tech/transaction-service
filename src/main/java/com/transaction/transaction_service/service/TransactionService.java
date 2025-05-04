package com.transaction.transaction_service.service;

import com.transaction.transaction_service.dto.TransactionDTO;
import com.transaction.transaction_service.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDTO dto);

    List<Transaction> getAllTransactions();

    void updateOrderStatus(String orderId, String newStatus);
}
