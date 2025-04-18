package com.josetesan.poc.springcustomer.controllers.dtos;

public record PurchaseDTO(String customerName, String productName, Integer amount, Double price) {}
