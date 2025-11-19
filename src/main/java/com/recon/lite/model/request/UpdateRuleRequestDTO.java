package com.recon.lite.model.request;

import com.recon.lite.utility.constants.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateRuleRequestDTO {
    @NotBlank(message = ExceptionConstants.RULE_NAME_IS_MANDATORY)
    private String ruleName;

    @Pattern(regexp = "AMOUNT_GREATER_THAN|MATCH_BY_DATE_AMOUNT", message = ExceptionConstants.RULE_SHOULD_GIVEN_VALUE)
    @NotNull(message = ExceptionConstants.RULE_TYPE_IS_MANDATORY)
    private String ruleType;

    @NotNull(message = ExceptionConstants.PRIORITY_IS_MANDATORY)
    private Integer priority;

    @NotNull(message = ExceptionConstants.RULE_STATUS_IS_MANDATORY)
    private Boolean isActive;

    private String ruleValue;
    @NotBlank(message = ExceptionConstants.DESCRIPTION_IS_MANDATORY)
    private String description;
}
