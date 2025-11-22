package com.recon.lite.model.response;


import lombok.Data;

@Data
public class ReconciliationHistoryResponseDTO {
    private Long id;
    private Integer matchedCount;
    private Integer unmatchedCount;
    private Integer rawCount;
    private String createdAt;
}
