package com.example.demo.controller;

import com.example.demo.Exceptions.EmailAlreadyExistsException;
import com.example.demo.Exceptions.UsernameAlreadyExistsException;
import com.example.demo.dto.AccountUpdateRequest;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
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

    @PostMapping
    public Account createAccount(@RequestBody Account account) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        return accountService.createAccount(account);
    }

   /* @PutMapping("/{accountId}")
    public Account updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody Account account
    ){
        String updatedUsername = String.valueOf(accountService.updateUsername(accountId, account));

        Map<String, Object> response = new HashMap<>();
        response.put("id", accountId);
        response.put("username", updatedUsername);

        return ResponseEntity.ok(response);
    } */

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateAccount(
            @PathVariable Long id,
            @RequestBody AccountUpdateRequest updateRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        Account updatedAccount = accountService.updateAccount(id, updateRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("account", updatedAccount);
        response.put("updateRequest", updateRequest);

        return ResponseEntity.ok(response);
    }

}
