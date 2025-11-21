package com.recon.lite.model.request;


import lombok.Data;

@Data
public class GetAllRulesFilterDTO {
    private String ruleType;
    private Boolean isActive;
    private Integer priority;
}
