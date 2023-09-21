package com.example.demo.service;

import com.example.demo.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){

        this.accountRepository = accountRepository;
    }
    public Account createAccount(Account account){

        String username = account.getUsername();
        if(accountRepository.findByUsername(username) != null){
            throw new IllegalArgumentException("Username already exists");
        }
        String email = account.getEmail();
        if(accountRepository.findByEmail(email) != null){
            throw new IllegalArgumentException("Email already exists");
        }
        return accountRepository.save(account);
    }
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    /*public Account updateAccount(Account existingAccount, String newUsername) {
        // Check if the existing account is null
        if (existingAccount == null) {
            throw new EntityNotFoundException("Account not found");
        }

        existingAccount.setUsername(newUsername);
        // Add a log statement to check the state of existingAccount before saving
        System.out.println("Before save: " + existingAccount);

        existingAccount = accountRepository.save(existingAccount);
        // Add a log statement to check the state of existingAccount after saving
        System.out.println("After save: " + existingAccount);

        return existingAccount;
    } */

    /*public Account updateUsername(Long accountId, String newUsername) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            String currentUsername = account.getUsername();

            if (currentUsername == null || !currentUsername.equals(newUsername)) {
                account.setUsername(newUsername);
                return accountRepository.save(account);
            } else {
                // Username is the same; no update needed
                return account;
            }
        } else {
            throw new IllegalArgumentException("Account with ID " + accountId + " not found");
        }
    } */

    public Account updateAccount(Long accountId, String username, String email, String bankAccountNumber) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            boolean hasChanges = false;

            if (username != null && !username.equals(account.getUsername())) {
                account.setUsername(username);
                hasChanges = true;
            }

            if (email != null && !email.equals(account.getEmail())) {
                account.setEmail(email);
                hasChanges = true;
            }

            if (bankAccountNumber != null && !bankAccountNumber.equals(account.getBankAccountNumber())) {
                account.setBankAccountNumber(bankAccountNumber);
                hasChanges = true;
            }

            if (hasChanges) {
                return accountRepository.save(account);
            } else {
                // No changes were made; return the original account
                return account;
            }
        } else {
            throw new EntityNotFoundException("Account with ID " + accountId + " not found");
        }
    }
}
