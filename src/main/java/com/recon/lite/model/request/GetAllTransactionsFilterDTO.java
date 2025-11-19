package com.recon.lite.model.request;


import lombok.Data;

@Data
public class GetAllTransactionsFilterDTO {
    private String source;
    private String status;
    private Double amountMin;
    private Double amountMax;
    private String dateFrom;
    private String dateTo;
}
