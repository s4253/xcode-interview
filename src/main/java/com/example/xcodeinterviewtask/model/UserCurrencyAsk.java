package com.example.xcodeinterviewtask.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table
@Data
public class UserCurrencyAsk {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserCurrencyRequest_seq_id")
    @SequenceGenerator(name = "UserCurrencyRequest_seq_id", sequenceName = "UserCurrencyRequest_seq_id", allocationSize = 1, initialValue = 1)
    @Id
    private Long id;

    private String currency;

    private String username;

    private BigDecimal exchangeRate;

    private Timestamp askDateTime;
}
