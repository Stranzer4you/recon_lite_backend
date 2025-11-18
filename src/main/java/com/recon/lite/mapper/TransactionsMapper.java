package com.recon.lite.mapper;

import com.recon.lite.dao.Transaction;
import com.recon.lite.model.request.TransactionRequest;
import com.recon.lite.model.response.TransactionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionsMapper {
    Transaction dtoToDao(TransactionRequest request);

    TransactionResponse daoToResponseDto(Transaction transaction);

    List<TransactionResponse> daoListToResponseList(List<Transaction> transactionList);
}
