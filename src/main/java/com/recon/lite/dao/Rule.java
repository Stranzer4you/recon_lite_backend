package com.recon.lite.dao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"Rules\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"RuleName\"", nullable = false)
    private String ruleName;

    @Column(name = "\"RuleType\"", nullable = false)
    private String ruleType;

    @Column(name = "\"RuleValue\"")
    private String ruleValue;

    @Column(name = "\"IsActive\"", nullable = false)
    private Boolean isActive = true;

    @Column(name = "\"Description\"", nullable = false)
    private String description;

    @Column(name = "\"Priority\"")
    private Integer priority;

    @Column(name = "\"CreatedAt\"")
    private LocalDateTime createdAt;

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;
}
