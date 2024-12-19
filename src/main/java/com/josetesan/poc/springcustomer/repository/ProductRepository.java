package com.josetesan.poc.springcustomer.repository;

import com.josetesan.poc.springcustomer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
