package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Product;
import com.josetesan.poc.springcustomer.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service class responsible for managing Product operations. */
@Service
@Transactional
public class ProductService {

  final ProductRepository productRepository;

  /**
   * Constructs an instance of ProductService with the provided repositories.
   *
   * @param productRepository The repository to interact with Products.
   */
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Retrieves all Products from the database.
   *
   * @return A list of all Products.
   */
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  /**
   * Retrieves a Product by its ID.
   *
   * @param id The ID of the Product to retrieve.
   * @return The retrieved Product, or throws a ResourceNotFoundException if not found.
   */
  public Product getProductById(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
  }

  /**
   * Creates a new Product for a customer.
   *
   * @param product The Product details to create.
   * @return The created Product.
   */
  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  /**
   * Deletes an existing Product by its ID.
   *
   * @param id The ID of the Product to delete.
   */
  public void deleteProduct(Long id) {
    Product product = getProductById(id);
    productRepository.delete(product);
  }

  /**
   * Updates the balance of an existing Product.
   *
   * @param id The ID of the Product to update.
   * @param newPrice The new price for the Product.
   * @return The updated Product with the new balance.
   */
  public Product updatePrice(Long id, Double newPrice) {
    Product product = getProductById(id);
    product.setPrice(newPrice);
    return productRepository.save(product);
  }
}
