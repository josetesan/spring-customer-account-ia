package com.josetesan.poc.springcustomer.repository.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDTO {
  private Long id;
  private String name;
  private BigDecimal balance;
  private Long customerId;
}
