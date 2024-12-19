package com.josetesan.poc.springcustomer.repository;

import com.josetesan.poc.springcustomer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
