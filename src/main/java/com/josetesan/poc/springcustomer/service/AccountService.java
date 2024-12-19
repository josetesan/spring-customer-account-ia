package com.josetesan.poc.springcustomer.service;

import com.josetesan.poc.springcustomer.exceptions.ResourceNotFoundException;
import com.josetesan.poc.springcustomer.model.Account;
import com.josetesan.poc.springcustomer.model.Customer;
import com.josetesan.poc.springcustomer.repository.AccountRepository;
import com.josetesan.poc.springcustomer.repository.CustomerRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service class responsible for managing account operations. */
@Service
@Transactional
public class AccountService {

  final AccountRepository accountRepository;
  final CustomerRepository customerRepository;

  /**
   * Constructs an instance of AccountService with the provided repositories.
   *
   * @param accountRepository The repository to interact with accounts.
   * @param customerRepository The repository to interact with customers.
   */
  public AccountService(
      AccountRepository accountRepository, CustomerRepository customerRepository) {
    this.accountRepository = accountRepository;
    this.customerRepository = customerRepository;
  }

  /**
   * Retrieves all accounts from the database.
   *
   * @return A list of all accounts.
   */
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  /**
   * Retrieves an account by its ID.
   *
   * @param id The ID of the account to retrieve.
   * @return The retrieved account, or throws a ResourceNotFoundException if not found.
   */
  public Account getAccountById(Long id) {
    return accountRepository
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
  public Account createAccount(Account account, Long customerId) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    account.setCustomer(customer);
    return accountRepository.save(account);
  }

  /**
   * Deletes an existing account by its ID.
   *
   * @param id The ID of the account to delete.
   */
  public void deleteAccount(Long id) {
    Account account = getAccountById(id);
    accountRepository.delete(account);
  }

  /**
   * Updates the balance of an existing account.
   *
   * @param id The ID of the account to update.
   * @param newBalance The new balance for the account.
   * @return The updated account with the new balance.
   */
  public Account updateBalance(Long id, BigDecimal newBalance) {
    Account account = getAccountById(id);
    account.setBalance(newBalance);
    return accountRepository.save(account);
  }

  /**
   * Adds an existing account to a customer's account list.
   *
   * @param customerId The ID of the customer.
   * @param account The account to add.
   * @return The saved account.
   */
  public Account addAccountToCustomer(Long customerId, Account account) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + customerId));

    account.setCustomer(customer);
    Account savedAccount = accountRepository.save(account);

    // Update customer's account list

    customer.setAccount(savedAccount);
    customerRepository.save(customer);

    return savedAccount;
  }
}
