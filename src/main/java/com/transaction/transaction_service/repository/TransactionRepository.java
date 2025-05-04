package com.transaction.transaction_service.repository;

import com.transaction.transaction_service.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByOrders_OrderId(String orderId);
}
