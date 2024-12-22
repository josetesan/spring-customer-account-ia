package com.josetesan.poc.springcustomer.repository;

import com.josetesan.poc.springcustomer.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findByNameEqualsIgnoreCase(String name);
}
