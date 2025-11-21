package com.recon.lite.model.response;

import com.recon.lite.dao.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class AllTransactionsResponse {
    List<TransactionResponse> transactions;
    Long totalPages;
}
