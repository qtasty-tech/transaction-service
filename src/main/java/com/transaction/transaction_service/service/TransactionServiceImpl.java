package com.transaction.transaction_service.service;

import com.transaction.transaction_service.dto.TransactionDTO;
import com.transaction.transaction_service.model.Transaction;
import com.transaction.transaction_service.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

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

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
