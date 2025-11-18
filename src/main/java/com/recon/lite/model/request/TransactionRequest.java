package com.recon.lite.model.request;

import com.recon.lite.utility.constants.ExceptionConstants;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionRequest {
    private String description;
    @NotNull(message = ExceptionConstants.AMOUNT_IS_MANDATORY)
    @DecimalMin(value = "0.00", message = ExceptionConstants.AMOUNT_SHOULD_BE_POSITIVE)
    private Double amount;
    @NotBlank(message = ExceptionConstants.SOURCE_IS_MANDATORY)
    @Pattern(regexp = "BANK|SYSTEM", message = ExceptionConstants.SOURCE_BANK_SYSTEM)
    private String source;
}
