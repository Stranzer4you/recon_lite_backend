package com.recon.lite.service;

import com.recon.lite.model.request.GetAllRulesFilterDTO;
import com.recon.lite.model.request.RuleRequestDTO;
import com.recon.lite.model.request.UpdateRuleRequestDTO;
import com.recon.lite.utility.BaseResponse;

public interface RuleService {
    BaseResponse createRule(RuleRequestDTO request);

    BaseResponse getRuleById(Long id);

    BaseResponse getAllRules(GetAllRulesFilterDTO dto);

    BaseResponse updateRule(Long id, UpdateRuleRequestDTO request);

    BaseResponse updateRuleStatus(Long id, Boolean isActive);
}
