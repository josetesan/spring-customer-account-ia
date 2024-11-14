package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Account;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.repository.AccountRepository;
import com.josetesan.poc.springcustomer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    public Account createAccount(Account account, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = getAccountById(id);
        account.setName(accountDetails.getName());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }

    public Account updateBalance(Long id, BigDecimal newBalance) {
        Account account = getAccountById(id);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    public Account addAccountToCustomer(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);

        // Update customer's account list
        if (customer.getAccounts() == null) {
            customer.setAccounts(new ArrayList<>());
        }
        customer.getAccounts().add(savedAccount);
        customerRepository.save(customer);

        return savedAccount;
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        return customer.getAccounts();
    }
}
