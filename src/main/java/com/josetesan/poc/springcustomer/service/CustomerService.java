package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.controllers.dtos.CustomerDTO;
import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.repository.CustomerRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Customer getCustomerByName(String name) {
    return customerRepository.findByNameEquals(name).stream()
        .filter(customer -> customer.getName().equals(name))
        .toList()
        .getFirst();
  }

  public Customer createCustomer(CustomerDTO customer) {
    return customerRepository.save(new Customer(customer.name(), customer.age()));
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

  public Page<Customer> getAllCustomers(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }
}
