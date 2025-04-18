package com.josetesan.poc.springcustomer.repository;

import com.josetesan.poc.springcustomer.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  List<Customer> findByNameEquals(String name);
}
