package com.recon.lite.mapper;

import com.recon.lite.dao.Rule;
import com.recon.lite.model.request.RuleResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RulesMapper {
    RuleResponseDTO convertToDTO(Rule savedRule);

    List<RuleResponseDTO> convertRulesDaosToResponseList(List<Rule> rules);
}
