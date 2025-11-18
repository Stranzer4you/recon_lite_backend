package com.recon.lite.service;

import com.recon.lite.model.request.TransactionRequest;
import com.recon.lite.utility.BaseResponse;

public interface TransactionService {
    BaseResponse createTransaction(TransactionRequest request);

    BaseResponse getTransactionById(Long id);

    BaseResponse getAllTransactions();

    BaseResponse updateTransaction(Long id, TransactionRequest request);

    BaseResponse deleteTransaction(Long id);
}
