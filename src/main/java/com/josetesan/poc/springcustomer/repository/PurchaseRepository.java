package com.josetesan.poc.springcustomer.repository;

import com.josetesan.poc.springcustomer.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {}
