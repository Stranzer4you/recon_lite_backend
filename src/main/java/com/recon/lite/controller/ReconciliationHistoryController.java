package com.recon.lite.controller;

import com.recon.lite.service.ReconciliationHistoryService;
import com.recon.lite.utility.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reconciliation")
public class ReconciliationHistoryController {

    @Autowired
    private ReconciliationHistoryService reconciliationHistoryService;

    @GetMapping("/history")
    public BaseResponse getAllReconciliationHistory() {
        return reconciliationHistoryService.getAllHistory();
    }
}

