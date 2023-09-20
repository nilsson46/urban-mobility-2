package com.example.demo.service;

import com.example.demo.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;

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

    public Account updateAccount(Account existingAccount, String newUsername) {
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
    }
}
