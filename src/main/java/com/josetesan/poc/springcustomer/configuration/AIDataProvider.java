package com.josetesan.poc.springcustomer.configuration;

import com.josetesan.poc.springcustomer.controllers.dtos.CustomerDTO;
import com.josetesan.poc.springcustomer.controllers.dtos.ProductDTO;
import com.josetesan.poc.springcustomer.controllers.dtos.PurchaseDTO;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.model.Product;
import com.josetesan.poc.springcustomer.service.AccountService;
import com.josetesan.poc.springcustomer.service.CustomerService;
import com.josetesan.poc.springcustomer.service.ProductService;
import com.josetesan.poc.springcustomer.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AIDataProvider {

  private final CustomerService customerService;
  private final AccountService accountService;
  private final ProductService productService;
  private final PurchaseService purchaseService;

  public AIDataProvider(
      CustomerService customerService,
      AccountService accountService,
      ProductService productService,
      PurchaseService purchaseService) {
    this.customerService = customerService;
    this.accountService = accountService;
    this.productService = productService;
    this.purchaseService = purchaseService;
  }

  public AiConfiguration.CustomersResponse getAllCustomers() {
    log.info("Retrieving all customers");
    Pageable pageable = PageRequest.of(0, 100);
    Page<Customer> customerPage = customerService.getAllCustomers(pageable);
    var list =
        customerPage.getContent().stream()
            .map(customer -> new CustomerDTO(customer.getName(), customer.getAge()))
            .toList();
    return new AiConfiguration.CustomersResponse(list);
  }

  public AiConfiguration.ProductsResponse getAllProducts() {
    log.info("Retrieving all products");
    Pageable pageable = PageRequest.of(0, 100);
    Page<Product> productsPage = productService.getAllProducts(pageable);
    var list =
        productsPage.stream()
            .map(product -> new ProductDTO(product.getName(), product.getPrice()))
            .toList();
    return new AiConfiguration.ProductsResponse(list);
  }

  @Transactional
  public AiConfiguration.ProductResponse createProduct(
      AiConfiguration.CreateProductRequest productRequest) {
    log.info("Creating a product {}", productRequest);
    ProductDTO productDTO = new ProductDTO(productRequest.name(), productRequest.price());
    var created = this.productService.createProduct(productDTO);
    return new AiConfiguration.ProductResponse(
        new ProductDTO(created.getName(), created.getPrice()));
  }

  @Transactional
  public AiConfiguration.CreatePurchaseResponse createPurchase(
      AiConfiguration.CreatePurchaseRequest createPurchaseRequest) {
    log.info("Creating a purchase {}", createPurchaseRequest);
    var customer = this.customerService.getCustomerByName(createPurchaseRequest.customerName());
    var product = this.productService.getProductByName(createPurchaseRequest.productName());
    var created = this.purchaseService.createPurchase(customer.getId(), product.getId(),createPurchaseRequest.amount());
    PurchaseDTO purchaseDTO =
        new PurchaseDTO(
            createPurchaseRequest.customerName(),
            createPurchaseRequest.productName(),
            createPurchaseRequest.amount(),
            created.getPurchasePrice());
    return new AiConfiguration.CreatePurchaseResponse(purchaseDTO);
  }
}
