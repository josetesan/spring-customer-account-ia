package com.josetesan.poc.springcustomer.repository.dtos;

import java.util.List;
import lombok.Data;

@Data
public class CustomerDTO {
  private Long id;
  private String name;
  private int age;
  private List<AccountDTO> accounts;
}
