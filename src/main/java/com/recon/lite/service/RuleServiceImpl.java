package com.recon.lite.service;

import com.recon.lite.dao.Rule;
import com.recon.lite.exceptions.BadRequestException;
import com.recon.lite.mapper.RulesMapper;
import com.recon.lite.model.request.*;
import com.recon.lite.repository.RulesRepository;
import com.recon.lite.utility.BaseResponse;
import com.recon.lite.utility.BaseResponseUtility;
import com.recon.lite.utility.JdbcUtil;
import com.recon.lite.utility.constants.ExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private RulesMapper rulesMapper;

    @Autowired
    private JdbcUtil jdbcUtil;


    @Override
    public BaseResponse createRule(RuleRequestDTO request) {
        if (rulesRepository.existsByRuleName(request.getRuleName())) {
            throw new BadRequestException(ExceptionConstants.RULE_ALREADY_EXISTS);
        }
        Rule rule = new Rule();
        applyRuleFields(rule, request.getRuleName(), request.getRuleType(),
                request.getPriority(), request.getIsActive(), request.getDescription(),
                request.getRuleValue());
        rule.setCreatedAt(LocalDateTime.now());
        rule.setUpdatedAt(LocalDateTime.now());
        Rule savedRule = rulesRepository.save(rule);
        return BaseResponseUtility.getBaseResponse(rulesMapper.convertToDTO(savedRule));
    }

    @Override
    public BaseResponse getRuleById(Long id) {
        Rule rule = getRuleOrThrow(id);
        return BaseResponseUtility.getBaseResponse(rulesMapper.convertToDTO(rule));
    }

    @Override
    public BaseResponse getAllRules(GetAllRulesFilterDTO dto) {
        List<RuleResponseDTO> rules = jdbcUtil.getAllRules(dto);
        AllRulesResponseDTO response = new AllRulesResponseDTO();
        response.setRules(rules);
        return BaseResponseUtility.getBaseResponse(response);
    }

    @Override
    public BaseResponse updateRule(Long id, UpdateRuleRequestDTO request) {
        Rule rule = getRuleOrThrow(id);
        if (rulesRepository.existsByRuleNameAndIdNot(request.getRuleName(), id)) {
            throw new BadRequestException(ExceptionConstants.RULE_ALREADY_EXISTS);
        }
        applyRuleFields(rule, request.getRuleName(), request.getRuleType(),
                request.getPriority(), request.getIsActive(), request.getDescription(),
                request.getRuleValue());
        rule = rulesRepository.save(rule);
        return BaseResponseUtility.getBaseResponse(rulesMapper.convertToDTO(rule));
    }

    @Override
    public BaseResponse updateRuleStatus(Long id, Boolean isActive) {
        Rule rule = getRuleOrThrow(id);
        rule.setIsActive(isActive);
        rulesRepository.save(rule);
        return BaseResponseUtility.getBaseResponse();
    }


    private Rule getRuleOrThrow(Long id) {
        return rulesRepository.findById(id).orElseThrow(() -> new BadRequestException(ExceptionConstants.INVALID_RULE));
    }

    private void applyRuleFields(Rule rule, String name, String type, Integer priority, Boolean isActive, String description, String ruleValue) {
        rule.setRuleName(name);
        rule.setRuleType(type);
        rule.setPriority(priority);
        rule.setIsActive(isActive != null ? isActive : Boolean.TRUE);
        rule.setDescription(description);
        if (!ObjectUtils.isEmpty(ruleValue)) {
            rule.setRuleValue(ruleValue);
        }
    }
}
