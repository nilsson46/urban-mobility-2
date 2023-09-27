package com.example.demo.service;

import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.entity.Account;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AccountService accountService;

    public AuthService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String validSupplier(long accountId){
       Account supplier = accountService.getAccountById(accountId).get();
       if(!supplier.getRole().equals("supplier")){
           throw new InvalidInputException("You are not a supplier");
       }
       return "You are a supplier";
    }
}
