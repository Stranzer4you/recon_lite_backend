package com.recon.lite.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"ReconciliationHistory\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReconciliationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"MatchedCount\"")
    private Integer matchedCount;

    @Column(name = "\"UnmatchedCount\"")
    private Integer unmatchedCount;

    @Column(name = "\"RawCount\"")
    private Integer rawCount;

    @Column(name = "\"CreatedAt\"")
    private LocalDateTime createdAt;
}


