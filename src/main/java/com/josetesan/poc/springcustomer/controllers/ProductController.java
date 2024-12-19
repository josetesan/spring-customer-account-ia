package com.josetesan.poc.springcustomer.controllers;

import com.josetesan.poc.springcustomer.model.Product;
import com.josetesan.poc.springcustomer.service.ProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Product Product = productService.getProductById(id);
    return ResponseEntity.ok(Product);
  }

  @PostMapping("/customer/{customerId}")
  public Product createProduct(@RequestBody Product Product) {
    return productService.createProduct(Product);
  }

  @PatchMapping("/{id}/price")
  public ResponseEntity<Product> updateBalance(
      @PathVariable Long id, @RequestBody Double newPrice) {
    Product updatedProduct = productService.updatePrice(id, newPrice);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }
}
