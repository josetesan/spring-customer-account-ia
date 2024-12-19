package com.josetesan.poc.springcustomer.ia;

import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.service.AccountService;
import com.josetesan.poc.springcustomer.service.CustomerService;
import com.josetesan.poc.springcustomer.service.ProductService;
import com.josetesan.poc.springcustomer.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    return new AiConfiguration.CustomersResponse(customerPage.getContent());
  }
}
