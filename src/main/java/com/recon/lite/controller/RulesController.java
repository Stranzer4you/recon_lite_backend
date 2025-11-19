package com.recon.lite.controller;

import com.recon.lite.model.request.GetAllRulesFilterDTO;
import com.recon.lite.model.request.RuleRequestDTO;
import com.recon.lite.model.request.UpdateRuleRequestDTO;
import com.recon.lite.service.RuleService;
import com.recon.lite.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
public class RulesController {

    @Autowired
    private RuleService ruleService;

    @PostMapping
    public BaseResponse createRule(@RequestBody RuleRequestDTO request) {
        return ruleService.createRule(request);
    }

    @GetMapping("/{id}")
    public BaseResponse getRuleById(@PathVariable Long id) {
        return ruleService.getRuleById(id);
    }

    @GetMapping
    public BaseResponse getAllRules(@ModelAttribute GetAllRulesFilterDTO dto) {
        return ruleService.getAllRules(dto);
    }

    @PutMapping("/{id}")
    public BaseResponse updateRule(@PathVariable Long id, @RequestBody UpdateRuleRequestDTO request) {
        return ruleService.updateRule(id, request);
    }

    @PatchMapping("/{id}")
    public BaseResponse updateRuleStatus(@PathVariable Long id, @RequestParam("isActive") Boolean isActive) {
        return ruleService.updateRuleStatus(id, isActive);
    }
}
