package com.example.demo.controller;

import com.example.demo.Exceptions.ResourceNotFoundException;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.dto.AccountDto;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable("id") long accountId){
        return accountService.getAccountById(accountId);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) throws InvalidInputException, ResourceNotFoundException {
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) throws InvalidInputException, ResourceNotFoundException {
        Account updatedAccount = accountService.updateAccountById(id, account);

        Map<String, Object> response = new HashMap<>();
        response.put("account", updatedAccount);
        response.put("updateRequest", account);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id")int accountId){
        accountService.deleteAccountById(accountId);
        return new ResponseEntity<>("Account was deleted successfully", HttpStatus.OK);
    }

}
