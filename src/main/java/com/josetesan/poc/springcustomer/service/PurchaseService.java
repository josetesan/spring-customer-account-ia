package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.model.Product;
import com.josetesan.poc.springcustomer.model.Purchase;
import com.josetesan.poc.springcustomer.repository.CustomerRepository;
import com.josetesan.poc.springcustomer.repository.ProductRepository;
import com.josetesan.poc.springcustomer.repository.PurchaseRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service class responsible for managing account operations. */
@Service
@Transactional
public class PurchaseService {

  final PurchaseRepository purchaseRepository;
  final ProductRepository productRepository;
  final CustomerRepository customerRepository;

  public PurchaseService(
      PurchaseRepository purchaseRepository,
      ProductRepository productRepository,
      CustomerRepository customerRepository) {
    this.purchaseRepository = purchaseRepository;
    this.productRepository = productRepository;
    this.customerRepository = customerRepository;
  }

  /**
   * Retrieves all purchases from the database.
   *
   * @return A list of all purchases.
   */
  public List<Purchase> getAllPurchases() {
    return purchaseRepository.findAll();
  }

  /**
   * Retrieves an account by its ID.
   *
   * @param id The ID of the account to retrieve.
   * @return The retrieved account, or throws a ResourceNotFoundException if not found.
   */
  public Purchase getPurchaseById(Long id) {
    return purchaseRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
  }

  /**
   * Creates a new account for a customer.
   *
   * @param account The account details to create.
   * @param customerId The ID of the customer associated with the account.
   * @return The created account.
   */
  public Purchase createPurchase(Long customerId, Long productId,Integer amount) {

    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + customerId));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id: " + customerId));

    Purchase purchase = new Purchase();
    purchase.setPurchaseDate(ZonedDateTime.now());
    purchase.setProduct(product);
    purchase.setCustomer(customer);
    purchase.setAmount(amount);
    purchase.setPurchasePrice(product.getPrice());
    Purchase createdPurchase = purchaseRepository.save(purchase);

    if (customer.getPurchases() == null) {
      customer.setPurchases(new ArrayList<>());
    }
    customer.getPurchases().add(purchase);
    customerRepository.save(customer);

    return createdPurchase;
  }

  /**
   * Retrieves all purchases associated with a specific customer.
   *
   * @param customerId The ID of the customer.
   * @return A list of purchases associated with the customer.
   */
  public List<Purchase> getPurchasesByCustomerId(Long customerId) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    return customer.getPurchases();
  }
}
