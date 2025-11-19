package com.recon.lite.model.request;

import lombok.Data;

import java.util.List;

@Data
public class AllRulesResponseDTO {
    List<RuleResponseDTO> rules;
}
