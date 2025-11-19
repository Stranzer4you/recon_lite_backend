package com.recon.lite.controller;


import com.recon.lite.model.request.TransactionRequestDTO;
import com.recon.lite.service.TransactionService;
import com.recon.lite.utility.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping()
    public BaseResponse createTransaction(@RequestBody @Valid TransactionRequestDTO request) {
        return service.createTransaction(request);
    }

    @GetMapping("/{id}")
    public BaseResponse getTransactionById(@PathVariable("id") Long id) {
        return service.getTransactionById(id);
    }

    @GetMapping()
    public BaseResponse getAllTransactions() {
        return service.getAllTransactions();
    }

    @PutMapping("/{id}")
    public BaseResponse updateTransaction(@PathVariable("id") Long id, @RequestBody @Valid TransactionRequestDTO request) {
        return service.updateTransaction(id, request);
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteTransaction(@PathVariable("id") Long id) {
        return service.deleteTransaction(id);
    }
}
