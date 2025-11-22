package com.recon.lite.service;



import com.recon.lite.utility.BaseResponse;
import com.recon.lite.utility.BaseResponseUtility;
import com.recon.lite.utility.JdbcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReconciliationHistoryServiceImpl implements ReconciliationHistoryService{


    @Autowired
    private JdbcUtil jdbcUtil;

    @Override
    public BaseResponse getAllHistory() {
        return BaseResponseUtility.getBaseResponse(jdbcUtil.getAllHistory());
    }
}
