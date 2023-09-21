package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.dto.AccountUpdateRequest;
import com.example.demo.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){

        this.accountRepository = accountRepository;
    }
    public Account createAccount(Account account) throws InvalidInputException, ResourceNotFoundException {

        String username = account.getUsername();
        if(accountRepository.findByUsername(username) != null){
            throw new InvalidInputException("Username already exists");
        }
        String email = account.getEmail();
        if(accountRepository.findByEmail(email) != null){
            throw new ResourceNotFoundException("Email already exists");
        }
        return accountRepository.save(account);
    }
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public Account updateAccount(Long accountId, AccountUpdateRequest updateRequest)  {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            boolean hasChanges = false;

            if (updateRequest.getUsername() != null && !updateRequest.getUsername().equals(account.getUsername())) {
                Account existingAccount = accountRepository.findByUsername(updateRequest.getUsername());

                if(existingAccount!= null){
                    throw new InvalidInputException("Username already exists with id: " + accountId);
                }
                account.setUsername(updateRequest.getUsername());
                hasChanges = true;
            }

            if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(account.getEmail())) {
                Account existingAccount = accountRepository.findByEmail(updateRequest.getEmail());

                if(existingAccount!= null){
                    throw new InvalidInputException("Email already exists with id: " + accountId);
                }
                account.setEmail(updateRequest.getEmail());
                hasChanges = true;
            }

            if (updateRequest.getBankAccountNumber() != null
                    && !updateRequest.getBankAccountNumber().equals(account.getBankAccountNumber())) {
                account.setBankAccountNumber(updateRequest.getBankAccountNumber());
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
