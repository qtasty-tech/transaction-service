package com.transaction.transaction_service.service;

import com.transaction.transaction_service.dto.TransactionDTO;
import com.transaction.transaction_service.event.TransactionCreatedEvent;
import com.transaction.transaction_service.model.Transaction;
import com.transaction.transaction_service.producer.TransactionEventProducer;
import com.transaction.transaction_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionEventProducer transactionEventProducer;


    @Override
    public Transaction createTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setUserId(dto.getUserId());
        transaction.setUserName(dto.getUserName());
        transaction.setTotalAmount(dto.getTotalAmount());

        transaction.setOrders(dto.getOrders().stream().map(orderDTO -> {
            Transaction.OrderSummary orderSummary = new Transaction.OrderSummary();
            orderSummary.setOrderId(orderDTO.getOrderId());
            orderSummary.setOrderDate(orderDTO.getOrderDate());
            orderSummary.setOrderTotal(orderDTO.getOrderTotal());
            orderSummary.setStatus(orderDTO.getStatus());

            orderSummary.setItems(orderDTO.getItems().stream().map(itemDTO -> {
                Transaction.OrderItem item = new Transaction.OrderItem();
                item.setName(itemDTO.getName());
                item.setQuantity(itemDTO.getQuantity());
                item.setPrice(itemDTO.getPrice());
                return item;
            }).collect(Collectors.toList()));

            return orderSummary;
        }).collect(Collectors.toList()));

        Transaction savedTransaction = transactionRepository.save(transaction);
        // Send event to Kafka
        TransactionCreatedEvent event = new TransactionCreatedEvent();
        event.setTransactionId(savedTransaction.getId());
        event.setUserId(savedTransaction.getUserId());
        event.setTotalAmount(savedTransaction.getTotalAmount());
        event.setTransactionDate(savedTransaction.getTransactionDate());
        event.setOrderIds(savedTransaction.getOrders().stream()
                .map(Transaction.OrderSummary::getOrderId)
                .collect(Collectors.toList()));

        transactionEventProducer.sendTransactionCreatedEvent(event);

        return savedTransaction;
    }
    @Override
    public void updateOrderStatus(String orderId, String newStatus) {
        List<Transaction> transactions = transactionRepository.findByOrders_OrderId(orderId);
        transactions.forEach(transaction ->
                transaction.getOrders().stream()
                        .filter(order -> order.getOrderId().equals(orderId))
                        .forEach(order -> order.setStatus(newStatus))
        );
        transactionRepository.saveAll(transactions);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
