package com.josetesan.poc.springcustomer.repository.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    private String name;
    private BigDecimal balance;
    private Long customerId;
}