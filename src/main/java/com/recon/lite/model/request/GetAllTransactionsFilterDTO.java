package com.recon.lite.model.request;


import lombok.Data;

@Data
public class GetAllTransactionsFilterDTO {
    private String source;
    private String status;
    private Integer pageNumber;
    private Integer pageSize;
}
