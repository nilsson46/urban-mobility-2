package com.example.demo.service;

import com.example.demo.urbanMobilityApplication;
import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {urbanMobilityApplication.class})
class AccountServiceIntegrationTest {


    @Autowired
    AccountService accountService;

    //private final AccountRepository accountRepository;


    /*public AccountServiceIntegrationTest(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    } */

    @Test
    public void Should_CreateAndReturnAccountFromDatabase(){
        Account account = Account.builder()

                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();

        Account savedAccount = accountService.createAccount(account);

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getId()).isNotNull();

        Account retrievedAccount = accountService.getAccountById(savedAccount.getId());
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.getUsername()).isEqualTo("kuro");

    }

    @Test
    public void Should_UpdateAccount_AndReturnUpdatedAccountDetails_FromDatabase(){
        Account account = Account.builder()

                .username("kuro")
                .role("User")
                .email("kuro@gmail.com")
                .bankAccountNumber("12345678")
                .isPaymentConfirmed(true)
                .paymentHistory(0)
                .activeOrders(0)
                .build();

        Account createdAccount = accountService.createAccount(account);
        Long accountId = createdAccount.getId();

        String newUsername = "Simon";
        Account uppdatedAccount = createdAccount.setUsername(newUsername);

        assertThat(uppdatedAccount).isNotNull();

        assertEquals(accountId, uppdatedAccount.getId());

        assertEquals(newUsername, uppdatedAccount.getUsername());

    }





}