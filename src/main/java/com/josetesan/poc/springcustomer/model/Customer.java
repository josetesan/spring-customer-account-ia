package com.josetesan.poc.springcustomer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int age;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Account> accounts;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Purchase> purchases;
}
