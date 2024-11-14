package com.josetesan.poc.springcustomer.repository.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private int age;
    private List<AccountDTO> accounts;
}