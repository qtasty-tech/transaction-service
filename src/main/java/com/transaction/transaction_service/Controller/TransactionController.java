package com.transaction.transaction_service.Controller;

import com.transaction.transaction_service.dto.TransactionDTO;
import com.transaction.transaction_service.model.Transaction;
import com.transaction.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}