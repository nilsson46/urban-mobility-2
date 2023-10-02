package com.example.demo.service;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.entity.Account;
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

}
