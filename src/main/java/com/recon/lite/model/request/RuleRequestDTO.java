package com.recon.lite.model.request;

import com.recon.lite.utility.constants.ExceptionConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RuleRequestDTO {

    @NotBlank(message = ExceptionConstants.RULE_NAME_IS_MANDATORY)
    private String ruleName;

    @NotNull(message = ExceptionConstants.RULE_TYPE_IS_MANDATORY)
    @Pattern(regexp = "AMOUNT_GREATER_THAN|MATCH_BY_DATE_AMOUNT", message = ExceptionConstants.RULE_SHOULD_GIVEN_VALUE)
    private String ruleType;

    @NotBlank(message = ExceptionConstants.DESCRIPTION_IS_MANDATORY)
    private String description;

    @NotNull(message = ExceptionConstants.PRIORITY_IS_MANDATORY)
    private Integer priority;

    private Boolean isActive;
    private String ruleValue;
}
