package com.recon.lite.service;

import com.recon.lite.dao.ReconciliationHistory;
import com.recon.lite.dao.Rule;
import com.recon.lite.dao.Transaction;
import com.recon.lite.exceptions.BadRequestException;
import com.recon.lite.mapper.TransactionsMapper;
import com.recon.lite.model.request.GetAllTransactionsFilterDTO;
import com.recon.lite.model.request.TransactionRequestDTO;
import com.recon.lite.model.response.AllTransactionsResponse;
import com.recon.lite.model.response.TransactionResponse;
import com.recon.lite.repository.ReconciliationHistoryRepository;
import com.recon.lite.repository.RulesRepository;
import com.recon.lite.repository.TransactionRepository;
import com.recon.lite.utility.BaseResponse;
import com.recon.lite.utility.BaseResponseUtility;
import com.recon.lite.utility.JdbcUtil;
import com.recon.lite.utility.constants.ExceptionConstants;
import com.recon.lite.utility.enums.TransactionStatusEnum;
import org.apache.tomcat.util.digester.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private TransactionsMapper mapper;

    @Autowired
    private JdbcUtil jdbcUtil;

    @Autowired
    private RulesRepository ruleRepository;

    @Autowired
    private ReconciliationHistoryRepository historyRepository;


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
    public BaseResponse getAllTransactions(GetAllTransactionsFilterDTO dto) {
        List<TransactionResponse> transactionList = jdbcUtil.getAllTransactions(dto.getSource(), dto.getStatus(),dto.getPageNumber(),dto.getPageSize());
        long totalRecords = repository.count();
        int pageSize = dto.getPageSize();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0 && totalRecords > 0) {
            totalPages = 1;
        }
        AllTransactionsResponse response = new AllTransactionsResponse();
        response.setTotalPages((long) totalPages);
        response.setTransactions(transactionList);
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

    @Override
    public BaseResponse runReconciliation() {
        List<Transaction> transactions = repository.findAll();
        log.info("transactions -> {} ",transactions);
        List<Rule> activeRules = ruleRepository.findAllByIsActiveTrue();
        log.info("activeRules -> {} ",activeRules);

        int matchedCount = 0;
        int unmatchedCount = 0;
        int rawCount = 0;

        for (Transaction txn : transactions) {
            boolean isMatched = false;

            for (Rule rule : activeRules) {
                switch(rule.getRuleType()) {
                    case "AMOUNT_GREATER_THAN":
                        double threshold = Double.parseDouble(rule.getRuleValue());
                        if (txn.getAmount() > threshold) {
                            isMatched = true;
                        }
                        break;

                    case "MATCH_BY_DATE_AMOUNT":
                        boolean found = transactions.stream()
                                .anyMatch(t -> !t.getId().equals(txn.getId())
                                        && t.getAmount().equals(txn.getAmount())
                                        && t.getCreatedAt().equals(txn.getCreatedAt()));
                        if (found) {
                            isMatched = true;
                        }
                        break;
                }
            }

            if (isMatched) {
                txn.setStatus("MATCHED");
                matchedCount++;
            } else if ("UNMATCHED".equals(txn.getStatus())) {
                unmatchedCount++;
            } else {
                rawCount++;
            }
        }

        repository.saveAll(transactions);

        ReconciliationHistory history = new ReconciliationHistory();
        history.setMatchedCount(matchedCount);
        history.setUnmatchedCount(unmatchedCount);
        history.setRawCount(rawCount);
        history.setCreatedAt(LocalDateTime.now());
        historyRepository.save(history);

        Map<String, Object> response = new HashMap<>();
        response.put("matched", matchedCount);
        response.put("unmatched", unmatchedCount);
        response.put("raw", rawCount);

        return BaseResponseUtility.getBaseResponse(response);

    }

    private Transaction getTransactionOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(ExceptionConstants.INVALID_TRANSACTION_ID));
    }


}
