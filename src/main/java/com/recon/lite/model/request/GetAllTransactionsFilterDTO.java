package com.recon.lite.model.request;


import com.recon.lite.utility.constants.ExceptionConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetAllTransactionsFilterDTO {
    private String source;
    private String status;
    private Integer pageNumber=1;
    private Integer pageSize=8;
}
