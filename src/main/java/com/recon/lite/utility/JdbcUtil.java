package com.recon.lite.utility;


import com.recon.lite.model.request.GetAllRulesFilterDTO;
import com.recon.lite.model.request.RuleResponseDTO;
import com.recon.lite.model.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RuleResponseDTO> getAllRules(GetAllRulesFilterDTO filter) {

        StringBuilder query = new StringBuilder("""
                SELECT
                    "ID", "RuleName", "RuleType",
                    "RuleValue", "IsActive", "Description",
                    "Priority", TO_CHAR("CreatedAt", 'DD/MM/YYYY') as "CreatedAt"
                FROM "Rules"
                WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();
        if (!ObjectUtils.isEmpty(filter.getRuleName())) {
            query.append(" AND LOWER(\"RuleName\") LIKE LOWER(?) ");
            params.add("%" + filter.getRuleName() + "%");
        }
        if (!ObjectUtils.isEmpty(filter.getRuleType())) {
            query.append(" AND \"RuleType\" = ? ");
            params.add(filter.getRuleType());
        }
        if (!ObjectUtils.isEmpty(filter.getIsActive())) {
            query.append(" AND \"IsActive\" = ? ");
            params.add(filter.getIsActive());
        }
        if (!ObjectUtils.isEmpty(filter.getPriority())) {
            query.append(" AND \"Priority\" = ? ");
            params.add(filter.getPriority());
        }
        query.append(" ORDER BY \"CreatedAt\" DESC ");
        return jdbcTemplate.query(query.toString(), params.toArray(), rulesRowMapper);
    }


    public List<TransactionResponse> getAllTransactions(String source, String status, Integer pageNumber, Integer pageSize) {
        StringBuilder sql = new StringBuilder("""
                    SELECT
                    "ID","Description","Amount", "Source","Status",TO_CHAR("CreatedAt", 'DD/MM/YYYY') AS "CreatedAtFormatted"
                    FROM "Transactions"
                    WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();
        if (!ObjectUtils.isEmpty(source)) {
            sql.append(" AND \"Source\" = ? ");
            params.add(source);
        }
        if (!ObjectUtils.isEmpty(status)) {
            sql.append(" AND \"Status\" = ? ");
            params.add(status);
        }
        sql.append(" ORDER BY \"CreatedAt\" DESC ");
        if (pageNumber != null && pageSize != null) {
            int offset = (pageNumber - 1) * pageSize;
            sql.append(" LIMIT ? OFFSET ? ");
            params.add(pageSize);
            params.add(offset);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), transactionRowMapper);
    }


    private final RowMapper<RuleResponseDTO> rulesRowMapper = (rs, rowNum) -> {
        RuleResponseDTO rule = new RuleResponseDTO();
        rule.setId(rs.getLong("ID"));
        rule.setRuleName(rs.getString("RuleName"));
        rule.setRuleType(rs.getString("RuleType"));
        rule.setRuleValue(rs.getString("RuleValue"));
        rule.setIsActive(rs.getBoolean("IsActive"));
        rule.setDescription(rs.getString("Description"));
        rule.setPriority(rs.getInt("Priority"));
        rule.setCreatedAt(rs.getString("CreatedAt"));
        return rule;
    };

    private final RowMapper<TransactionResponse> transactionRowMapper = (rs, rowNum) -> {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(rs.getLong("ID"));
        dto.setDescription(rs.getString("Description"));
        dto.setAmount(rs.getDouble("Amount"));
        dto.setSource(rs.getString("Source"));
        dto.setStatus(rs.getString("Status"));
        dto.setCreatedAt(rs.getString("CreatedAtFormatted"));
        return dto;
    };


}
