package com.example.demo.controller;

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
    public Account createAccount(@RequestBody Account account) {
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

}
