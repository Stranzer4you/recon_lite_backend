package com.recon.lite.dao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"Transactions\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ID\"")
    private Long id;

    @Column(name = "\"TransactionDate\"")
    private LocalDate transactionDate;

    @Column(name = "\"Description\"")
    private String description;

    @Column(name = "\"Amount\"")
    private Double amount;

    @Column(name = "\"Source\"")
    private String source;

    @Column(name = "\"Status\"")
    private String status;

    @Column(name = "\"CreatedAt\"")
    private LocalDateTime createdAt;

    @Column(name = "\"UpdatedAt\"")
    private LocalDateTime updatedAt;
}
