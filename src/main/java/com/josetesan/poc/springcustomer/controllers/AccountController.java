package com.josetesan.poc.springcustomer.controllers;

import com.josetesan.poc.springcustomer.model.Account;
import com.josetesan.poc.springcustomer.service.AccountService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public List<Account> getAllAccounts() {
    return accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    Account account = accountService.getAccountById(id);
    return ResponseEntity.ok(account);
  }

  @PostMapping("/customer/{customerId}")
  public Account createAccount(@RequestBody Account account, @PathVariable Long customerId) {
    return accountService.createAccount(account, customerId);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Account> updateAccount(
      @PathVariable Long id, @RequestBody Account accountDetails) {
    Account updatedAccount = accountService.updateAccount(id, accountDetails);
    return ResponseEntity.ok(updatedAccount);
  }

  @PatchMapping("/{id}/balance")
  public ResponseEntity<Account> updateBalance(
      @PathVariable Long id, @RequestBody BigDecimal newBalance) {
    Account updatedAccount = accountService.updateBalance(id, newBalance);
    return ResponseEntity.ok(updatedAccount);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/customer/{customerId}/add")
  public ResponseEntity<Account> addAccountToCustomer(
      @PathVariable Long customerId, @RequestBody Account account) {
    Account newAccount = accountService.addAccountToCustomer(customerId, account);
    return ResponseEntity.ok(newAccount);
  }

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
    List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
    return ResponseEntity.ok(accounts);
  }
}
