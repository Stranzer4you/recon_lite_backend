package com.recon.lite.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RuleResponseDTO {
    private Long id;
    private String ruleName;
    private String ruleValue;
    private String ruleType;
    private Boolean isActive;
    private String description;
    private Integer priority;
    private String createdAt;
}
