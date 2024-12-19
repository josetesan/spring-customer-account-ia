package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Account;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

  final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  public Customer getCustomerById(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
  }

  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  public Customer updateCustomer(Long id, Customer customerDetails) {
    Customer customer = getCustomerById(id);
    customer.setName(customerDetails.getName());
    customer.setAge(customerDetails.getAge());
    return customerRepository.save(customer);
  }

  public void deleteCustomer(Long id) {
    Customer customer = getCustomerById(id);
    customerRepository.delete(customer);
  }

  public List<Account> getCustomerAccounts(Long customerId) {
    Customer customer = getCustomerById(customerId);
    return customer.getAccounts();
  }
}
