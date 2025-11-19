package com.recon.lite.service;

import com.recon.lite.dao.Transaction;
import com.recon.lite.exceptions.BadRequestException;
import com.recon.lite.mapper.TransactionsMapper;
import com.recon.lite.model.request.TransactionRequestDTO;
import com.recon.lite.model.response.AllTransactionsResponse;
import com.recon.lite.model.response.TransactionResponse;
import com.recon.lite.repository.TransactionRepository;
import com.recon.lite.utility.BaseResponse;
import com.recon.lite.utility.BaseResponseUtility;
import com.recon.lite.utility.constants.ExceptionConstants;
import com.recon.lite.utility.enums.TransactionStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionsMapper mapper;


    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BaseResponse createTransaction(TransactionRequestDTO request) {
        Transaction transaction = mapper.dtoToDao(request);
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatusEnum.RAW.name());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setTransactionDate(LocalDate.now());
        transaction = repository.save(transaction);
        TransactionResponse transactionResponse = mapper.daoToResponseDto(transaction);
        return BaseResponseUtility.getBaseResponse(transactionResponse);
    }

    @Override
    public BaseResponse getTransactionById(Long id) {
        Transaction transaction = getTransactionOrThrow(id);
        TransactionResponse transactionResponse = mapper.daoToResponseDto(transaction);
        return BaseResponseUtility.getBaseResponse(transactionResponse);
    }

    @Override
    public BaseResponse getAllTransactions() {
        List<Transaction> transactionList = repository.findAll();
        List<TransactionResponse> responseList = mapper.daoListToResponseList(transactionList);
        AllTransactionsResponse response = new AllTransactionsResponse();
        response.setTransactions(responseList);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BaseResponse updateTransaction(Long id, TransactionRequestDTO request) {
        Transaction transaction = getTransactionOrThrow(id);
        transaction.setSource(request.getSource());
        transaction.setDescription(request.getDescription());
        transaction.setAmount(request.getAmount());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction = repository.save(transaction);
        return BaseResponseUtility.getBaseResponse(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public BaseResponse deleteTransaction(Long id) {
        Transaction transaction = getTransactionOrThrow(id);
        repository.deleteById(transaction.getId());
        return BaseResponseUtility.getBaseResponse();
    }

    private Transaction getTransactionOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(ExceptionConstants.INVALID_TRANSACTION_ID));
    }
}
