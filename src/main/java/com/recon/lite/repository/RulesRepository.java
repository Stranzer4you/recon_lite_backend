package com.recon.lite.repository;

import com.recon.lite.dao.Rule;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends JpaRepository<Rule, Long> {
    boolean existsByRuleName(String ruleName);

    boolean existsByRuleNameAndIdNot(String ruleName, Long id);
}
