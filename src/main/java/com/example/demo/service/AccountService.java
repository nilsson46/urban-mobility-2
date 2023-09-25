package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.dto.AccountDto;
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
            throw new InvalidInputException("Email already exists");
        }
        return accountRepository.save(account);
    }
    public Optional<Account> getAccountById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account with ID " + accountId + " does not exist");
        }
        return accountRepository.findById(accountId);
    }

    /*public Account updateAccount(Long accountId, Account account) {

        if (!accountRepository.existsById(accountId)) {
            throw new ResourceNotFoundException("Account with ID " + accountId + " not found");
        }

            //check how to get the right account and then change the account.
            // Account account = optionalAccount.get();
            boolean hasChanges = false;

            if (account.getUsername() != null && !account.getUsername().equals(account.getUsername())) {
                Account existingAccount = accountRepository.findByUsername(account.getUsername());

                if (existingAccount != null) {
                    throw new InvalidInputException("Username already exists with id: " + accountId);
                }
                account.setUsername(account.getUsername());
                hasChanges = true;
            }

            if (account.getEmail() != null && !account.getEmail().equals(account.getEmail())) {
                Account existingAccount = accountRepository.findByEmail(account.getEmail());

                if (existingAccount != null) {
                    throw new InvalidInputException("Email already exists with id: " + accountId);
                }
                account.setEmail(account.getEmail());
                hasChanges = true;
            }

            if (account.getBankAccountNumber() != null
                    && !account.getBankAccountNumber().equals(account.getBankAccountNumber())) {
                account.setBankAccountNumber(account.getBankAccountNumber());
                hasChanges = true;
            }

            if (hasChanges) {
                return accountRepository.save(account);
            } else {
                // No changes were made; return the original account
                return account;
            }

        } */

    /*public Account updateAccount(Long accountId, Account updatedAccount)  {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();

            if (updatedAccount.getUsername() != null && !updatedAccount.getUsername().equals(existingAccount.getUsername())) {
                Account accountWithNewUsername = accountRepository.findByUsername(updatedAccount.getUsername());

                if(accountWithNewUsername!= null && !accountWithNewUsername.getId().equals(accountId)){
                    throw new InvalidInputException("Username already exists with id: " + accountWithNewUsername);
                }
                existingAccount.setUsername(updatedAccount.getUsername());
            }

            if (updatedAccount.getEmail() != null && !updatedAccount.getEmail().equals(existingAccount.getEmail())) {
                Account accountWithNewEmail  = accountRepository.findByEmail(updatedAccount.getEmail());

                if(accountWithNewEmail != null && !accountWithNewEmail.getId().equals(accountId)){
                    throw new InvalidInputException("Email already exists with id: " + accountWithNewEmail.getId());
                }
                existingAccount.setEmail(updatedAccount.getEmail());
            }
                // No changes were made; return the original account
                return accountRepository.save(existingAccount);

        } else {
            throw new EntityNotFoundException("Account with ID " + accountId + " not found");
        }
    } */

    public Account updateAccountById(Long accountId, Account account) {
        if (!accountRepository.existsById(accountId)){
            throw new ResourceNotFoundException("Account with ID" + " " + accountId + " " + "does not exist");
        }
        account.setId(accountId);
        return accountRepository.save(account);
    }


    public void deleteAccountById(long accountId) {
        if(!accountRepository.existsById(accountId)){
            throw new ResourceNotFoundException("Account with ID" + " " + accountId + " " + "does not exist");
        }
        accountRepository.deleteById(accountId);
    }
    /*public Account updateAccount(Long accountId, AccountUpdateRequest updateRequest)  {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            boolean hasChanges = false;

            if (updateRequest.getUsername() != null && !updateRequest.getUsername().equals(account.getUsername())) {
                Account existingAccount = accountRepository.findByUsername(updateRequest.getUsername());

                if (existingAccount != null && !existingAccount.getId().equals(accountId)) {
                    // Ett konto med det nya användarnamnet finns redan, och det är inte det aktuella kontot
                    throw new InvalidInputException("Username already exists with id: " + existingAccount.getId());
                }

                account.setUsername(updateRequest.getUsername());
                hasChanges = true;
            }

            // ... (liknande kontroller för e-post och bankkontonummer)

            if (hasChanges) {
                return accountRepository.save(account);
            } else {
                // Ingen ändring gjordes; returnera det ursprungliga kontot
                return account;
            }
        } else {
            throw new EntityNotFoundException("Account with ID " + accountId + " not found");
        }
    } */
}
