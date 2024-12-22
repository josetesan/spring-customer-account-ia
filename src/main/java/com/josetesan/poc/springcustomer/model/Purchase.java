package com.josetesan.poc.springcustomer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "purchases")
@NoArgsConstructor
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long purchaseId;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  @JsonBackReference
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Product product;

  private Integer amount;
  private ZonedDateTime purchaseDate;
  private Double purchasePrice;
}
