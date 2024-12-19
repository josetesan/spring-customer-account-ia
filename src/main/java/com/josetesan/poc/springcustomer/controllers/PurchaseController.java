package com.josetesan.poc.springcustomer.controllers;

import com.josetesan.poc.springcustomer.model.Purchase;
import com.josetesan.poc.springcustomer.service.PurchaseService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

  PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  // Create a new purchase
  @PostMapping("/customer/{customerId}/product/{productId}")
  public ResponseEntity<Purchase> createPurchase(
      @PathVariable Long customerId, @PathVariable Long productId) {
    Purchase savedPurchase = purchaseService.createPurchase(customerId, productId);
    return ResponseEntity.ok(savedPurchase);
  }

  // Get all purchases
  @GetMapping("/")
  public List<Purchase> getAllPurchases() {
    return purchaseService.getAllPurchases();
  }

  // Get a single purchase by ID
  @GetMapping("/{id}")
  public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
    Purchase purchase = purchaseService.getPurchaseById(id);
    return ResponseEntity.ok(purchase);
  }
}
