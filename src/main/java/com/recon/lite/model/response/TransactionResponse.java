package com.recon.lite.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private String description;
    private Double amount;
    private String source;
    private String status;
    private String createdAt;
    private String updatedAt;
}
