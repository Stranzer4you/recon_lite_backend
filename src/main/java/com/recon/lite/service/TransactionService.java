package com.recon.lite.service;

import com.recon.lite.model.request.TransactionRequestDTO;
import com.recon.lite.utility.BaseResponse;

public interface TransactionService {
    BaseResponse createTransaction(TransactionRequestDTO request);

    BaseResponse getTransactionById(Long id);

    BaseResponse getAllTransactions();

    BaseResponse updateTransaction(Long id, TransactionRequestDTO request);

    BaseResponse deleteTransaction(Long id);
}
